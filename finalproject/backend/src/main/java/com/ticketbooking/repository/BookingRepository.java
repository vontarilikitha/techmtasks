package com.ticketbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ticketbooking.model.Booking;
import com.ticketbooking.model.BookingStatus;
import com.ticketbooking.model.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    List<Booking> findByUser(User user);
    
    List<Booking> findByUserAndStatus(User user, BookingStatus status);
    
    List<Booking> findByEventId(Long eventId);
    
    List<Booking> findByEventIdAndStatus(Long eventId, BookingStatus status);
    
    List<Booking> findByUserId(Long userId); // ✅ Corrected this method
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.event.id = :eventId")
    long countByEventId(@Param("eventId") Long eventId);

}

