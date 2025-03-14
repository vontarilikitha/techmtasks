import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

interface Event {
  id: number;
  name: string;
  category: string;
  description: string;
  venue: string;
  eventDateTime: string;
  bookingCount: number;
  totalSeats: number;
  availableSeats: number;
  price: number;
  bookings: any[];
  analytics?: {
    totalRevenue: number;
    occupancyRate: number;
    salesByShowTime: { [key: string]: number };
  };
}

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  events: Event[] = [];
  selectedEvent: Event | null = null;
  showAnalytics = false;
  ticketCount = 1;
  ticketOptions = [1, 2, 3, 4, 5, 6]; // Options for ticket selection

  constructor(private router: Router) {}

  ngOnInit() {
    this.fetchEvents();
  }

  async fetchEvents() {
    try {
      const response = await fetch('http://localhost:8080/api/events', {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (response.ok) {
        const data = await response.json();
        this.events = await Promise.all(data.map(async (event: any) => {
          const [bookings, analytics] = await Promise.all([
            this.fetchBookings(event.id),
            this.fetchEventAnalytics(event.id)
          ]);

          return {
            ...event,
            bookings,
            analytics,
            bookingCount: bookings.length,
            availableSeats: event.totalSeats - bookings.reduce((total: number, booking: any) => 
              total + (booking.numberOfTickets || 0), 0)
          };
        }));
      }
    } catch (error) {
      console.error('Error fetching events:', error);
    }
  }

  async fetchBookings(eventId: number) {
    try {
      const response = await fetch(`http://localhost:8080/api/bookings/event/${eventId}`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      return response.ok ? await response.json() : [];
    } catch (error) {
      console.error('Error fetching bookings:', error);
      return [];
    }
  }

  async fetchEventAnalytics(eventId: number) {
    try {
      const response = await fetch(`http://localhost:8080/api/events/${eventId}/analytics`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      return response.ok ? await response.json() : null;
    } catch (error) {
      console.error('Error fetching analytics:', error);
      return null;
    }
  }

  async viewAnalytics(event: Event) {
    try {
      const response = await fetch(`http://localhost:8080/api/events/${event.id}/analytics`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (response.ok) {
        const analyticsData = await response.json();
        this.selectedEvent = {
          ...event,
          analytics: analyticsData
        };
        this.showAnalytics = true;
      } else {
        console.error('Failed to fetch analytics');
        alert('Failed to load analytics data');
      }
    } catch (error) {
      console.error('Error fetching analytics:', error);
      alert('Error loading analytics data');
    }
  }

  closeAnalytics() {
    this.showAnalytics = false;
    this.selectedEvent = null;
  }

  async removeEvent(eventId: number) {
    if (!eventId) {
      console.error('Invalid event ID');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/events/${eventId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (response.ok) {
        this.events = this.events.filter(event => event.id !== eventId);
        console.log('Event removed successfully');
      } else {
        const errorData = await response.text();
        console.error('Failed to remove event:', errorData);
        alert('Failed to remove event. Please try again.');
      }
    } catch (error) {
      console.error('Error removing event:', error);
      alert('Error occurred while removing event.');
    }
  }

  logout() {
    try {
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      localStorage.removeItem('user');
      this.router.navigate(['/login']);
    } catch (error) {
      console.error('Error during logout:', error);
    }
  }

  bookTickets() {
    if (!this.selectedEvent) {
      console.error('No event selected');
      return;
    }

    if (this.ticketCount > 6) {
      alert('You can book a maximum of 6 tickets');
      return;
    }

    // Logic to book tickets
    console.log(`Booking ${this.ticketCount} tickets for event: ${this.selectedEvent.name}`);
    // Implement booking logic here, e.g., call a booking API
  }
}