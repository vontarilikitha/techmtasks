import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';
import { WebSocket } from 'ws';

interface Booking {
  id: number;
  eventId: number;
  event: {
    name: string;
    type: string;
  };
  numberOfTickets: number;
  totalPrice: number;
  status: string;
  showTime?: string;
  showId: number;
  userId: number;
  seats: string[];
  bookingDate: Date;
  totalAmount: number;
  ticketPDF?: string;
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  constructor(private http: HttpClient) {}

  getBookings(userId: number): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${environment.apiUrl}/bookings/user/${userId}`);
  }

  downloadTicket(bookingId: number): Observable<Blob> {
    return this.http.get(`${environment.apiUrl}/bookings/${bookingId}/ticket`, 
      { responseType: 'blob' });
  }

  cancelBooking(bookingId: number): Observable<any> {
    return this.http.put(`${environment.apiUrl}/bookings/${bookingId}/cancel`, {});
  }

  requestRefund(bookingId: number): Observable<any> {
    return this.http.post(`${environment.apiUrl}/bookings/${bookingId}/refund`, {});
  }
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private socket: WebSocket;
  notifications$ = new BehaviorSubject<Notification[]>([]);

  constructor(private http: HttpClient) {
    this.initializeWebSocket();
  }

  private initializeWebSocket() {
    this.socket = new WebSocket(`${environment.wsUrl}/notifications`);
    this.socket.onmessage = (event) => {
      const notification = JSON.parse(event.data);
      this.addNotification(notification);
    };
  }

  getNotifications(userId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>(`${environment.apiUrl}/notifications/user/${userId}`);
  }

  markAsRead(notificationId: number): Observable<any> {
    return this.http.put(`${environment.apiUrl}/notifications/${notificationId}/read`, {});
  }
}

@Component({
  selector: 'app-my-bookings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-bookings.component.html',
  styleUrls: ['./my-bookings.component.css']
})
export class MyBookingsComponent implements OnInit {
  bookings: Booking[] = [];

  ngOnInit() {
    this.fetchUserBookings();
  }

  async fetchUserBookings() {
    try {
      console.log('Fetching bookings...');
      const userId = localStorage.getItem('userId');
      if (!userId) {
        console.error('No user ID found');
        return;
      }

      const response = await fetch(`http://localhost:8080/api/bookings/users/${userId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      const responseText = await response.text();
      console.log('Raw response:', responseText);

      if (response.ok) {
        const data = JSON.parse(responseText);
        console.log('Parsed bookings:', data);
        this.bookings = data;
      } else {
        console.error('Failed to fetch bookings:', responseText);
      }
    } catch (error) {
      console.error('Error fetching bookings:', error);
    }
  }

  onStatusChange(status: string, bookingId: number | undefined) {
    if (status && bookingId) {
      this.updateStatus(bookingId, status);
    }
  }

  async updateStatus(bookingId: number, status: string) {
    try {
      const response = await fetch(`http://localhost:8080/api/bookings/${bookingId}/status?status=${status}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      if (response.ok) {
        await this.fetchUserBookings();
      }
    } catch (error) {
      console.error('Error updating status:', error);
    }
  }

  async cancelBooking(bookingId: number | undefined) {
    if (!bookingId) return;
    try {
      const response = await fetch(`http://localhost:8080/api/bookings/${bookingId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      if (response.ok) {
        await this.fetchUserBookings();
      }
    } catch (error) {
      console.error('Error cancelling booking:', error);
    }
  }

  async confirmPayment(bookingId: number | undefined) {
    if (!bookingId) return;
    try {
      const paymentId = 'PAY' + Math.random().toString(36).substr(2, 9);
      const response = await fetch(`http://localhost:8080/api/bookings/${bookingId}/confirm-payment?paymentId=${paymentId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      if (response.ok) {
        await this.fetchUserBookings();
      }
    } catch (error) {
      console.error('Error confirming payment:', error);
    }
  }

  async downloadTicket(bookingId: number) {
    try {
      const response = await fetch(`http://localhost:8080/api/bookings/${bookingId}/ticket`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      if (response.ok) {
        const blob = await response.blob();
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = `ticket_${bookingId}.pdf`;
        link.click();
        window.URL.revokeObjectURL(link.href);
      }
    } catch (error) {
      console.error('Error downloading ticket:', error);
    }
  }

  async requestRefund(bookingId: number) {
    try {
      const response = await fetch(`http://localhost:8080/api/bookings/${bookingId}/request-refund`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      if (response.ok) {
        await this.fetchUserBookings();
      }
    } catch (error) {
      console.error('Error requesting refund:', error);
    }
  }
}