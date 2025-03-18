import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SeatingChartComponent } from '../seating-chart/seating-chart.component';
import { Event } from '../../models/event.model';

type TimeSlots = {
  [key: string]: string[];
  morning: string[];
  matinee: string[];
  evening: string[];
  night: string[];
};

@Component({
  selector: 'app-book-tickets',
  standalone: true,
  imports: [CommonModule, FormsModule, SeatingChartComponent],
  templateUrl: './book-tickets.component.html',
  styleUrls: ['./book-tickets.component.css']
})
export class BookTicketsComponent implements OnInit {
  show: Event | null = null;
  loading = false;
  error: string | null = null;
  selectedSeats: Array<{row: string; number: number}> = [];
  selectedTime: string = '';
  selectedTicketType: string = 'GENERAL';
  timeTypes = ['morning', 'matinee', 'evening', 'night'];
  showTimesByType: TimeSlots = {
    morning: ['10:00 AM', '11:30 AM'],
    matinee: ['2:00 PM', '3:30 PM'],
    evening: ['6:00 PM', '7:30 PM'],
    night: ['9:00 PM', '10:30 PM']
  };
  showSeatSelection = false;
  ticketCount: number = 1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const eventId = this.route.snapshot.paramMap.get('id');
    if (eventId) {
      this.fetchEventDetails(eventId);
    }
  }

  getSelectedSeatsDisplay(): string {
    if (!this.selectedSeats || this.selectedSeats.length === 0) {
      return 'No seats selected';
    }
    return this.selectedSeats
      .map(seat => `${seat.row}${seat.number}`)
      .join(', ');
  }

  async fetchEventDetails(id: string) {
    this.loading = true;
    this.error = null;
    try {
      const response = await fetch(`http://localhost:8080/api/events/${id}`);
      if (!response.ok) {
        throw new Error('Failed to fetch event details');
      }
      this.show = await response.json();
      console.log('Fetched event details:', this.show);
      
      if (this.show) {
        this.showSeatSelection = this.show.category === 'MOVIE' || this.show.category === 'THEATRE';
        if (this.show.category === 'THEATRE') {
          this.selectedTime = this.show.eventDateTime;
        }
      } else {
        this.showSeatSelection = false;
      }
    } catch (error) {
      console.error('Error fetching event details:', error);
      this.error = 'Failed to load event details. Please try again.';
    } finally {
      this.loading = false;
      this.cdr.detectChanges();
    }
  }

  selectTicketType(type: string) {
    this.selectedTicketType = type;
    this.selectedSeats = []; // Reset selected seats when changing ticket type
  }

  getTicketPrice(): number {
    if (!this.show) return 0;
    
    switch (this.selectedTicketType) {
      case 'VIP':
        return this.show.vipPrice || this.show.price * 2;
      case 'PREMIUM':
        return this.show.premiumPrice || this.show.price * 1.5;
      case 'GENERAL':
        return this.show.generalPrice || this.show.price;
      default:
        return this.show.price;
    }
  }

  getTotalPrice(): number {
    if (!this.show) return 0;
    
    const price = this.getTicketPrice();
    if (this.show.category === 'MOVIE' || this.show.category === 'THEATRE') {
      return price * (this.selectedSeats.length || 1);
    } else {
      return price * this.ticketCount;
    }
  }

  onSeatsSelected(seats: Array<{row: string; number: number}>) {
    this.selectedSeats = seats;
    this.ticketCount = seats.length;
    this.error = null;
    this.cdr.detectChanges();
  }

  async bookTickets() {
    if (!this.show) {
      this.error = 'Event details not found';
      return;
    }

    if ((this.show.category === 'MOVIE' || this.show.category === 'THEATRE') && !this.selectedTime) {
      this.error = 'Please select a show time';
      return;
    }

    if (this.show.category === 'CONCERT' || this.show.category === 'SPORTS') {
      if (this.ticketCount <= 0) {
        this.error = 'Please select at least one ticket';
        return;
      }
    } else {
      if (this.selectedSeats.length === 0) {
        this.error = 'Please select seats to continue';
        return;
      }
    }

    const userId = localStorage.getItem('userId');
    if (!userId) {
      this.router.navigate(['/login']);
      return;
    }

    this.loading = true;
    this.error = null;

    try {
      const bookingData = {
        eventId: this.show.id,
        userId: userId,
        seatNumbers: this.show.category === 'MOVIE' || this.show.category === 'THEATRE' 
          ? this.selectedSeats.map(seat => `${seat.row}${seat.number}`)
          : Array(this.ticketCount).fill('UNRESERVED'),
        showTime: this.selectedTime || this.show.eventDateTime,
        ticketType: this.selectedTicketType,
        numberOfTickets: this.show.category === 'MOVIE' || this.show.category === 'THEATRE'
          ? this.selectedSeats.length
          : this.ticketCount,
        totalPrice: this.getTotalPrice(),
        paymentStatus: 'COMPLETED'
      };

      console.log('Sending booking data:', bookingData);

      const response = await fetch(`http://localhost:8080/api/bookings/users/${userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(bookingData)
      });

      const data = await response.json();
      console.log('Backend response:', data);

      if (!response.ok) {
        if (data.message) {
          throw new Error(data.message);
        } else if (data.error) {
          throw new Error(data.error);
        } else if (typeof data === 'string') {
          throw new Error(data);
        } else {
          throw new Error('Failed to create booking. Please try again.');
        }
      }

      alert('Booking successful! You can view your bookings in My Bookings section.');
      this.router.navigate(['/my-bookings']);
    } catch (error: any) {
      console.error('Error creating booking:', error);
      this.error = error.message || 'Failed to create booking. Please try again.';
      
      // Log additional details for debugging
      if (error.response) {
        console.error('Response status:', error.response.status);
        console.error('Response headers:', error.response.headers);
      }
    } finally {
      this.loading = false;
      this.cdr.detectChanges();
    }
  }

  formatTime(time: string): string {
    return time;
  }

  canShowBookingSummary(): boolean {
    if (!this.show) return false;

    if (this.show.category === 'MOVIE' || this.show.category === 'THEATRE') {
      return this.selectedTime !== '' && this.selectedSeats.length > 0;
    } else {
      return this.ticketCount > 0;
    }
  }

  selectShowTime(time: string) {
    this.selectedTime = time;
    this.selectedSeats = []; // Reset seats when changing time
    this.error = null;
    this.cdr.detectChanges();
  }
}
