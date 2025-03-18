package com.ticketbooking.service;

import java.util.List;

import com.ticketbooking.dto.EventDTO;
import com.ticketbooking.model.Event;
import com.ticketbooking.model.EventAnalytics;
import com.ticketbooking.model.EventCategory;
import com.ticketbooking.model.Seat;

public interface EventService {
    EventDTO createEvent(EventDTO eventDto);
    EventDTO getEventById(Long id);
    Event getEventEntityById(Long id);
    List<EventDTO> getAllEvents();
    List<EventDTO> getUpcomingEvents();
    List<EventDTO> getEventsByCategory(EventCategory category);
    List<EventDTO> searchEvents(String keyword);
    EventDTO updateEvent(Long id, EventDTO eventDto);
    void deleteEvent(Long id);
    boolean updateEventSeats(Long eventId, int seatsToBook, boolean isBooking);
    List<EventDTO> getAllEventsWithBookingCounts();
    EventAnalytics getEventAnalytics(Long eventId);
    void setTicketPrice(Long eventId, double ticketPrice);
    List<Seat> getEventSeats(Long eventId);
    void updateSeatStatus(Long eventId, Long seatId, boolean isAvailable);
    boolean validateTicketAvailability(Long eventId, int numberOfTickets);
    void initializeSeatsForEvent(Event event);
    void updateEvent(Event event);
    EventDTO mapEventToDTO(Event event);
    
}
