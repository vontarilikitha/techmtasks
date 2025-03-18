import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

interface Seat {
  id: number;
  row: string;
  number: number;
  isAvailable: boolean;
  isSelected: boolean;
  price: number;
}

@Component({
  selector: 'app-seating-chart',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="seating-chart">
      <div class="event-info">
        <h2>{{ eventName || 'Event Name' }}</h2>
        <p>{{ venue || 'Venue Name' }}</p>
        <p>Price per ticket: {{ price | currency }}</p>
      </div>

      <div class="screen">
        <div class="screen-text">SCREEN THIS WAY</div>
      </div>

      <div class="ticket-info">
        <p>Maximum 6 tickets per booking</p>
        <p>Remaining tickets: {{ getRemainingTickets() }}</p>
      </div>

      <div class="seats-container">
        <div *ngFor="let row of seatMap" class="row">
          <span class="row-label">{{row[0].row}}</span>
          <div *ngFor="let seat of row" 
               class="seat" 
               [class.available]="seat.isAvailable"
               [class.selected]="seat.isSelected"
               [class.unavailable]="!seat.isAvailable"
               (click)="selectSeat(seat)">
            {{seat.row}}{{seat.number}}
          </div>
        </div>
      </div>
      <div class="legend">
        <div class="legend-item">
          <div class="seat available"></div>
          <span>Available</span>
        </div>
        <div class="legend-item">
          <div class="seat selected"></div>
          <span>Selected</span>
        </div>
        <div class="legend-item">
          <div class="seat unavailable"></div>
          <span>Booked</span>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .seating-chart {
      padding: 20px;
      background: #f5f5f5;
      border-radius: 8px;
    }
    .screen {
      background: #ddd;
      padding: 20px;
      text-align: center;
      margin-bottom: 30px;
      border-radius: 4px;
    }
    .seats-container {
      display: flex;
      flex-direction: column;
      gap: 10px;
      align-items: center;
    }
    .row {
      display: flex;
      gap: 10px;
      align-items: center;
    }
    .row-label {
      width: 30px;
      text-align: center;
      font-weight: bold;
    }
    .seat {
      width: 35px;
      height: 35px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 4px;
      cursor: pointer;
      font-size: 12px;
      border: 2px solid transparent;
      transition: all 0.2s ease;
    }
    .available {
      background: white;
      border-color: #2dc492;
      color: #333;
    }
    .selected {
      background: #2dc492;
      border-color: #2dc492;
      color: white;
    }
    .unavailable {
      background: #f44336;
      border-color: #d32f2f;
      color: white;
      cursor: not-allowed;
    }
    .legend {
      display: flex;
      justify-content: center;
      gap: 20px;
      margin: 20px 0;
    }
    .legend-item {
      display: flex;
      align-items: center;
      gap: 8px;
    }
    .legend-item .seat {
      width: 25px;
      height: 25px;
      cursor: default;
    }
  `]
})
export class SeatingChartComponent implements OnInit {
  @Input() totalSeats: number = 70; // Default to 70 seats (7 rows x 10 seats)
  @Input() availableSeats: number = 70;
  @Input() eventName: string = '';
  @Input() venue: string = '';
  @Input() price: number = 0;
  @Output() seatSelected = new EventEmitter<Seat[]>();
  @Output() proceedToBooking = new EventEmitter<void>();

  seatMap: Seat[][] = [];
  selectedSeats: Seat[] = [];
  maxTickets = 6;

  constructor(private router: Router) {}

  ngOnInit() {
    if (!this.totalSeats || this.totalSeats <= 0) {
      console.warn('Invalid totalSeats provided, defaulting to 70');
      this.totalSeats = 70;
    }
    if (!this.availableSeats || this.availableSeats < 0) {
      console.warn('Invalid availableSeats provided, defaulting to totalSeats');
      this.availableSeats = this.totalSeats;
    }
    if (this.availableSeats > this.totalSeats) {
      console.warn('availableSeats cannot be greater than totalSeats, adjusting to match totalSeats');
      this.availableSeats = this.totalSeats;
    }
    this.generateSeatMap();
  }

  private generateSeatMap() {
    this.seatMap = [];
    const rows = ['A', 'B', 'C', 'D', 'E', 'F', 'G'];
    let seatId = 1;
    let availableCount = this.availableSeats;
    let totalSeatsPerRow = 10;
    let bookedSeatsCount = this.totalSeats - this.availableSeats;
    
    // Randomly distribute booked seats
    const bookedSeats = new Set();
    while (bookedSeats.size < bookedSeatsCount) {
      const randomRow = Math.floor(Math.random() * rows.length);
      const randomSeat = Math.floor(Math.random() * totalSeatsPerRow) + 1;
      const seatKey = `${rows[randomRow]}${randomSeat}`;
      bookedSeats.add(seatKey);
    }
    
    for (const rowLetter of rows) {
      const row: Seat[] = [];
      for (let seatNum = 1; seatNum <= totalSeatsPerRow && seatId <= this.totalSeats; seatNum++) {
        const seatKey = `${rowLetter}${seatNum}`;
        const isAvailable = !bookedSeats.has(seatKey);
        row.push({
          id: seatId,
          row: rowLetter,
          number: seatNum,
          isAvailable: isAvailable,
          isSelected: false,
          price: this.price || 0
        });
        seatId++;
      }
      if (row.length > 0) {
        this.seatMap.push(row);
      }
    }

    console.log('Generated seat map with booked seats:', 
      this.seatMap.flatMap(row => 
        row.filter(seat => !seat.isAvailable)
           .map(seat => `${seat.row}${seat.number}`)
      )
    );
  }

  selectSeat(seat: Seat) {
    if (!seat.isAvailable) {
      alert('This seat is not available');
      return;
    }
    
    if (seat.isSelected) {
      seat.isSelected = false;
      this.selectedSeats = this.selectedSeats.filter(s => s.id !== seat.id);
    } else if (this.selectedSeats.length < this.maxTickets) {
      seat.isSelected = true;
      this.selectedSeats.push(seat);
    } else {
      alert(`Maximum ${this.maxTickets} tickets allowed per booking`);
      return;
    }
    
    console.log('Selected seats:', this.selectedSeats.map(s => `${s.row}${s.number}`).join(', '));
    this.seatSelected.emit(this.selectedSeats);
  }

  getRemainingTickets(): number {
    return this.maxTickets - this.selectedSeats.length;
  }

  getSelectedSeatsLabel(): string {
    return this.selectedSeats
      .map(seat => `${seat.row}${seat.number}`)
      .join(', ');
  }

  getTotalAmount(): number {
    return this.selectedSeats.length * (this.price || 0);
  }

  proceedToPayment() {
    if (this.selectedSeats.length === 0) {
      alert('Please select at least one seat');
      return;
    }
    this.seatSelected.emit(this.selectedSeats);
    this.proceedToBooking.emit();
  }
}