package com.ticketbooking.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "booking_seats",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "seat_id"))
    private Set<Seat> seats = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull(message = "Event cannot be null")
    private Event event;

    @Positive(message = "Number of tickets must be positive")
    private int numberOfTickets;

    @Positive(message = "Total price must be positive")
    private double totalPrice;

    private boolean paymentCompleted;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "show_time")
    private String showTime;
    
    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "payment_id", unique = true, nullable = true)
    private String paymentId;

    @PrePersist
    protected void onCreate() {
        bookingDate = LocalDateTime.now();
    }
    
    

    // Constructors
    public Booking() {}

    public Booking(User user, Event event, int numberOfTickets, double totalPrice, boolean paymentCompleted, BookingStatus status, String showTime, LocalDateTime bookingDate, String paymentId) {
        this.user = user;
        this.event = event;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.paymentCompleted = paymentCompleted;
        this.status = status;
        this.showTime = showTime;
        this.bookingDate = bookingDate;
        this.paymentId = paymentId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + user +
                ", event=" + event +
                ", numberOfTickets=" + numberOfTickets +
                ", totalPrice=" + totalPrice +
                ", paymentCompleted=" + paymentCompleted +
                ", status=" + status +
                ", showTime='" + showTime + '\'' +
                ", bookingDate=" + bookingDate +
                ", paymentId='" + paymentId + '\'' +
                '}';
    }

	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}
}