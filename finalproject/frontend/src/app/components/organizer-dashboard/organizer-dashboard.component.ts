import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Event } from '../../models/event.model';

interface EventAnalytics {
  totalBookings: number;
  totalRevenue: number;
  averageRating: number;
}

interface EventWithAnalytics extends Event {
  analytics?: EventAnalytics;
}

@Component({
  selector: 'app-organizer-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './organizer-dashboard.component.html',
  styleUrls: ['./organizer-dashboard.component.css']
})
export class OrganizerDashboardComponent implements OnInit {
  events: Event[] = [];
  selectedEvent: Event | null = null;
  showEventForm = false;
  currentEvent: Event = {
    name: '',
    description: '',
    category: 'MOVIE',
    venue: '',
    eventDateTime: '',
    price: 0,
    vipPrice: 0,
    premiumPrice: 0,
    generalPrice: 0,
    totalSeats: 0,
    availableSeats: 0
  } as Event;

  isEditing = false;

  constructor(private router: Router) {}

  ngOnInit() {
    this.fetchEvents();
  }

  async fetchEvents() {
    try {
      const response = await fetch('http://localhost:8080/api/events');
      if (!response.ok) throw new Error('Failed to fetch events');
      const data = await response.json();
      this.events = data.map((event: any) => ({
        ...event,
        id: Number(event.id)
      }));
    } catch (error) {
      console.error('Error fetching events:', error);
    }
  }

  editEvent(event: Event) {
    this.isEditing = true;
    this.currentEvent = { ...event };
    this.showEventForm = true;
  }

  async deleteEvent(eventId: number) {
    if (!confirm('Are you sure you want to delete this event?')) return;

    try {
      const response = await fetch(`http://localhost:8080/api/events/${eventId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (response.ok) {
        this.events = this.events.filter(event => event.id !== eventId);
      } else {
        alert('Failed to delete event');
      }
    } catch (error) {
      console.error('Error deleting event:', error);
      alert('Error deleting event');
    }
  }

  async saveEvent() {
    if (!this.isEditing) {
      const { id, ...eventWithoutId } = this.currentEvent;
      this.currentEvent = eventWithoutId as Event;
    }

    const url = this.isEditing ? 
      `http://localhost:8080/api/events/${this.currentEvent.id}` :
      'http://localhost:8080/api/events';

    try {
      console.log('Sending event data:', this.currentEvent);
      const response = await fetch(url, {
        method: this.isEditing ? 'PUT' : 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(this.currentEvent)
      });

      if (!response.ok) {
        const errorData = await response.json();
        console.error('Server error:', errorData);
        alert('Failed to save event: ' + errorData.message);
      } else {
        await this.fetchEvents();
        this.showEventForm = false;
        this.resetForm();
      }
    } catch (error) {
      console.error('Error saving event:', error);
      alert('Error saving event');
    }
  }

  async fetchEventAnalytics(eventId: number) {
    try {
      const response = await fetch(`http://localhost:8080/api/events/${eventId}/analytics`);
      if (!response.ok) throw new Error('Failed to fetch analytics');
      const event = this.events.find(e => e.id === eventId);
      if (event) {
        const analytics = await response.json();
        const totalTicketsSold = event.totalSeats - event.availableSeats;
        this.selectedEvent = {
          ...event,
          analytics: {
            totalBookings: totalTicketsSold,
            totalRevenue: analytics.totalRevenue,
            averageRating: analytics.averageRating
          }
        };
      }
    } catch (error) {
      console.error('Error fetching analytics:', error);
    }
  }
  resetForm() {
    this.currentEvent = {
        name: '',
        description: '',
        category: 'MOVIE',
        venue: '',
        eventDateTime: '',
        price: 0,
        vipPrice: 0,
        premiumPrice: 0,
        generalPrice: 0,
        totalSeats: 0,
        availableSeats: 0
    } as Event;
    this.isEditing = false;
  }

  cancelEdit() {
    this.showEventForm = false;
    this.resetForm();
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    this.router.navigate(['/login']);
  }
}