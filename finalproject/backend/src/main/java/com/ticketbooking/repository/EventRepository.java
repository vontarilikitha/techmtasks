package com.ticketbooking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ticketbooking.model.Event;
import com.ticketbooking.model.EventCategory;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCategory(EventCategory category);
    
    List<Event> findByEventDateTimeAfter(LocalDateTime now);
    
    @Query("SELECT e FROM Event e WHERE e.eventDateTime > CURRENT_TIMESTAMP AND e.availableSeats > 0 ORDER BY e.eventDateTime ASC")
    List<Event> findUpcomingEventsWithAvailableSeats();
    
    List<Event> findByVenueContainingIgnoreCase(String venue);
    
    List<Event> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
    
}