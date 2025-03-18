package com.ticketbooking.controller;

import com.ticketbooking.model.Seat;
import com.ticketbooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin(origins = "http://localhost:4200")
public class SeatController {
    
    @Autowired
    private SeatRepository seatRepository;

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Seat>> getEventSeats(@PathVariable Long eventId) {
        return ResponseEntity.ok(seatRepository.findByEventId(eventId));
    }

    @GetMapping("/event/{eventId}/available")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable Long eventId) {
        return ResponseEntity.ok(seatRepository.findByEventIdAndAvailable(eventId, true));
    }
}