package com.ticketbooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ticketbooking.dto.EventDTO;
import com.ticketbooking.dto.SeatDTO;
import com.ticketbooking.model.Event;
import com.ticketbooking.model.EventAnalytics;
import com.ticketbooking.model.EventCategory;
import com.ticketbooking.model.Seat;
import com.ticketbooking.repository.SeatRepository;
import com.ticketbooking.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;
    
    @Autowired
    private SeatRepository seatRepository;
    
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDto) {
        return new ResponseEntity<>(eventService.createEvent(eventDto), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
    
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventDTO>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventDTO>> getEventsByCategory(@PathVariable EventCategory category) {
        return ResponseEntity.ok(eventService.getEventsByCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDto) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/ticket-price")
    public ResponseEntity<Void> setTicketPrice(@PathVariable Long id, @RequestParam double ticketPrice) {
        eventService.setTicketPrice(id, ticketPrice);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventId}/analytics")
    public ResponseEntity<EventAnalytics> getEventAnalytics(@PathVariable Long eventId) {
        try {
            EventAnalytics analytics = eventService.getEventAnalytics(eventId);
            return ResponseEntity.ok(analytics);
        } catch (Exception e) {
            System.out.println("Error getting analytics: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    
    }


    @GetMapping("/{eventId}/seats")
    public List<SeatDTO> getEventSeats(@PathVariable Long eventId) {
        List<Seat> seats = seatRepository.findByEventId(eventId);
        return seats.stream() .map(seat -> new SeatDTO(
                seat.getId(),
                seat.getSeatIdentifier(),
                seat.isAvailable(),
                seat.getPrice()
            ))
            .collect(Collectors.toList());}
    @PutMapping("/{eventId}/seats/{seatId}")
    public ResponseEntity<Void> updateSeatStatus(
        @PathVariable Long eventId,
        @PathVariable Long seatId,
        @RequestParam boolean isAvailable
    ) {
        eventService.updateSeatStatus(eventId, seatId, isAvailable);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{eventId}/initialize-seats")
    public ResponseEntity<?> initializeSeats(@PathVariable Long eventId) {
        Event event = eventService.getEventEntityById(eventId);
        eventService.initializeSeatsForEvent(event);
        return ResponseEntity.ok().build();
    }
    
}
