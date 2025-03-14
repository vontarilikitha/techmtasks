package com.ticketbooking.repository;

import com.example.EventBooking.Event_TicketBooking_System.Event;
import com.ticketbooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByEventId(Long eventId);
    Optional<Seat> findByEventIdAndId(Long eventId, Long seatId);
    List<Seat>findByEventIdAndAvailable(Long eventId, boolean available);
    Optional<Seat> findByEventIdAndSeatNumber(Long eventId, Integer seatNumber);

    Optional<Seat> findByEventIdAndSeatIdentifier(Long eventId, String seatIdentifier);
    
   

    List<Seat> findAllByEvent(Event event);
    }
