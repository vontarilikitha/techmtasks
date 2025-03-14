package com.ticketbooking.service;

import com.ticketbooking.dto.BookingDTO;
import com.ticketbooking.dto.BookingRequestDTO;
import com.ticketbooking.model.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(Long userId, BookingRequestDTO requestDTO);
    BookingDTO getBookingById(Long id);
    List<BookingDTO> getAllBookings();
    List<BookingDTO> getBookingsByUser(Long userId);  // ✅ Ensure this method is present
    List<BookingDTO> getBookingsByEvent(Long eventId);
    BookingDTO updateBookingStatus(Long id, BookingStatus status);
    BookingDTO confirmPayment(Long id, String paymentId);
    void cancelBooking(Long id);
    
}
