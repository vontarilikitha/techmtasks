package com.ticketbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ticketbooking.dto.BookingDTO;
import com.ticketbooking.dto.BookingRequestDTO;
import com.ticketbooking.exception.ResourceNotFoundException;
import com.ticketbooking.model.Booking;
import com.ticketbooking.model.BookingStatus;
import com.ticketbooking.model.Event;
import com.ticketbooking.model.EventCategory;
import com.ticketbooking.service.BookingService;
import com.ticketbooking.service.EventService;
import com.ticketbooking.model.Payment;
import com.ticketbooking.model.PaymentRequest;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @PostMapping("/bookings/users/{userId}")
    public ResponseEntity<?> createBooking(@PathVariable Long userId, 
                                         @Valid @RequestBody BookingRequestDTO bookingRequest) {
        try {
            // Add detailed logging
            System.out.println("=== Starting Booking Creation ===");
            System.out.println("User ID: " + userId);
            System.out.println("Event ID: " + bookingRequest.getEventId());
            System.out.println("Number of Tickets: " + bookingRequest.getNumberOfTickets());
            System.out.println("Show Time: " + bookingRequest.getShowTime());
            System.out.println("Payment ID: " + bookingRequest.getPaymentId());

            // First, verify the event exists
            Event event = eventService.getEventEntityById(bookingRequest.getEventId());
            System.out.println("Event found: " + event.getName() + " (Category: " + event.getCategory() + ")");
            System.out.println("Available seats: " + event.getAvailableSeats());

            // Check if it's concert/sports
            boolean isConcertOrSports = EventCategory.CONCERT.equals(event.getCategory()) || 
                                       EventCategory.SPORTS.equals(event.getCategory());
            System.out.println("Is Concert/Sports: " + isConcertOrSports);

            // Validate ticket availability
            if (isConcertOrSports) {
                boolean ticketsAvailable = eventService.validateTicketAvailability(
                    event.getId(), 
                    bookingRequest.getNumberOfTickets()
                );
                System.out.println("Tickets available: " + ticketsAvailable);
                
                if (!ticketsAvailable) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("error", "Not enough tickets available"));
                }
            }

            // Create the booking
            BookingDTO booking = bookingService.createBooking(userId, bookingRequest);
            System.out.println("Booking created successfully: " + booking.getId());
            
            return ResponseEntity.ok(booking);
            
        } catch (ResourceNotFoundException e) {
            System.err.println("Resource not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.err.println("Booking creation failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Booking failed: " + e.getMessage()));
        }
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/bookings/users/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @PutMapping("/bookings/{id}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long id, 
            @RequestParam BookingStatus status) {
        BookingDTO updatedBooking = bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok(updatedBooking);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PutMapping("/bookings/{id}/confirm-payment")
    public ResponseEntity<BookingDTO> confirmPayment(
            @PathVariable Long id, 
            @RequestParam String paymentId) {
        BookingDTO updatedBooking = bookingService.confirmPayment(id, paymentId);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bookings/event/{eventId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByEvent(@PathVariable Long eventId) {
        List<BookingDTO> bookings = bookingService.getBookingsByEvent(eventId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/bookings/payment")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request) {
        try {
            if (!validatePaymentDetails(request)) {
                return ResponseEntity.badRequest().body("Invalid payment details");
            }

            // Process payment and update booking
            String transactionId = generateTransactionId();
            BookingDTO updatedBooking = bookingService.confirmPayment(
                request.getBookingId(), 
                transactionId
            );

            return ResponseEntity.ok(Map.of(
                "message", "Payment processed successfully",
                "transactionId", transactionId,
                "booking", updatedBooking
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Payment processing failed: " + e.getMessage());
        }
    }

    private boolean validatePaymentDetails(PaymentRequest request) {
        return request != null && 
               request.getCardNumber() != null && 
               request.getCardNumber().length() >= 16;
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
}