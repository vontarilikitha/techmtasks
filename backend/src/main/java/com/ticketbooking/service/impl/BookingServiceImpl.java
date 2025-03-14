package com.ticketbooking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticketbooking.dto.BookingDTO;
import com.ticketbooking.dto.BookingRequestDTO;
import com.ticketbooking.dto.EventDTO;
import com.ticketbooking.exception.ResourceNotFoundException;
import com.ticketbooking.model.Booking;
import com.ticketbooking.model.BookingStatus;
import com.ticketbooking.model.Event;
import com.ticketbooking.model.EventCategory;
import com.ticketbooking.model.Seat;
import com.ticketbooking.model.User;
import com.ticketbooking.repository.BookingRepository;
import com.ticketbooking.repository.SeatRepository;
import com.ticketbooking.service.BookingService;
import com.ticketbooking.service.EventService;
import com.ticketbooking.service.UserService;

import jakarta.annotation.PostConstruct;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private SeatRepository seatRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EventService eventService;

    @Override
    @Transactional
    public BookingDTO createBooking(Long userId, BookingRequestDTO requestDTO) {
        try {
            System.out.println("=== BookingService: Creating Booking ===");
            
            // 1. Get User
            User user = userService.getUserEntityById(userId);
            System.out.println("User found: " + user.getEmail());

            // 2. Get Event
            Event event = eventService.getEventEntityById(requestDTO.getEventId());
            System.out.println("Event found: " + event.getName());
            System.out.println("Event price: " + event.getPrice());  // Changed from getTicketPrice to getPrice

            // 3. Calculate Price - Use the correct price field
            Double eventPrice = event.getPrice();  // Changed from getTicketPrice to getPrice
            if (eventPrice == null || eventPrice <= 0) {
                throw new IllegalStateException("Event price must be set and positive");
            }
            
            double totalPrice = eventPrice * requestDTO.getNumberOfTickets();
            if (totalPrice <= 0) {
                throw new IllegalStateException("Total price calculation resulted in 0 or negative value");
            }
            System.out.println("Total price calculated: " + totalPrice);

            // 4. Create Booking
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setEvent(event);
            booking.setNumberOfTickets(requestDTO.getNumberOfTickets());
            booking.setShowTime(requestDTO.getShowTime());
            booking.setTotalPrice(totalPrice);
            booking.setPaymentCompleted(false);
            booking.setStatus(BookingStatus.PENDING);
            booking.setPaymentId(requestDTO.getPaymentId());

            // 5. Validate Available Seats
            Integer currentAvailable = event.getAvailableSeats();
            if (currentAvailable == null || currentAvailable < requestDTO.getNumberOfTickets()) {
                throw new IllegalStateException("Not enough seats available");
            }

            // 6. Update Event Seats
            event.setAvailableSeats(currentAvailable - requestDTO.getNumberOfTickets());
            System.out.println("New available seats: " + event.getAvailableSeats());

            // 7. Save Booking
            System.out.println("About to save booking with total price: " + booking.getTotalPrice());
            Booking savedBooking = bookingRepository.save(booking);
            System.out.println("Booking saved with ID: " + savedBooking.getId());

            return mapToDTO(savedBooking);

        } catch (Exception e) {
            System.err.println("=== Error in createBooking ===");
            System.err.println("Error type: " + e.getClass().getSimpleName());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create booking: " + e.getMessage());
        }
    }
    private Set<Seat> validateAndGetSeats(Event event, List<String> seatIdentifiers) {
        System.out.println("Validating seats for event: " + event.getId());
        System.out.println("Requested seat identifiers: " + seatIdentifiers);

        if (seatIdentifiers == null || seatIdentifiers.isEmpty()) {
            throw new IllegalArgumentException("No seats selected");
        }

        if (seatIdentifiers.size() > 6) {
            throw new IllegalArgumentException("Maximum 6 seats allowed per booking");
        }

        Set<Seat> seats = new HashSet<>();
        for (String seatIdentifier : seatIdentifiers) {
            Optional<Seat> seatOptional = seatRepository.findByEventIdAndSeatIdentifier(event.getId(), seatIdentifier);
            
            if (seatOptional.isEmpty()) {
                throw new ResourceNotFoundException("Seat not found: " + seatIdentifier);
            }

            Seat seat = seatOptional.get();
            if (!seat.isAvailable()) {
                throw new IllegalArgumentException("Seat " + seatIdentifier + " is not available");
            }

            seats.add(seat);
        }

        return seats;
    }

    private EventDTO mapEventToDTO(Event event) {
        EventDTO dto = new EventDTO();
        // Map all necessary fields from event to dto
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setEventDateTime(event.getEventDateTime());
        dto.setVenue(event.getVenue());
        dto.setTotalSeats(event.getTotalSeats());
        dto.setAvailableSeats(event.getAvailableSeats());
        dto.setPrice(event.getPrice());
        dto.setCategory(event.getCategory());
        return dto;
    }
    
    @Override
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return mapToDTO(booking);
    }

    @Override
    public List<BookingDTO> getBookingsByUser(Long userId) {
        System.out.println("Getting bookings for user: " + userId);
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        System.out.println("Found bookings: " + bookings.size());
        List<BookingDTO> dtos = bookings.stream()
            .map(booking -> {
                BookingDTO dto = mapToDTO(booking);
                System.out.println("Mapped booking: " + dto);
                return dto;
            })
            .collect(Collectors.toList());
        System.out.println("Returning DTOs: " + dtos);
        return dtos;
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByEvent(Long eventId) {
        return bookingRepository.findByEventId(eventId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public BookingDTO updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        booking.setStatus(status);
        Booking updatedBooking = bookingRepository.save(booking);
        
        return mapToDTO(updatedBooking);
    }


    @Override
    @Transactional
    public BookingDTO confirmPayment(Long id, String paymentId) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        booking.setPaymentCompleted(true);
        booking.setPaymentId(paymentId);
        booking.setStatus(BookingStatus.CONFIRMED); // Mark as confirmed

        Booking updatedBooking = bookingRepository.save(booking);
        return mapToDTO(updatedBooking);
    }

    @Override
    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        System.out.println("Before Cancel - Status: " + booking.getStatus()); // Debug log
        
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        System.out.println("After Cancel - Status: " + booking.getStatus()); // Debug log
    }


    private BookingDTO mapToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getId());
        dto.setEventId(booking.getEvent().getId());
        dto.setNumberOfTickets(booking.getNumberOfTickets());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setPaymentCompleted(booking.isPaymentCompleted());
        dto.setStatus(booking.getStatus());
        dto.setShowTime(booking.getShowTime());
        dto.setPaymentId(booking.getPaymentId());
        // Added paymentId to DTO
        return dto;
    }

    @PostConstruct
    public void testRepo() {
        System.out.println("Bookings: " + bookingRepository.findAll());
    }
    
}
