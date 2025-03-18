import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="register-container">
      <div class="register-card">
        <h2>Create Account</h2>
        <form (ngSubmit)="onSubmit()" #registerForm="ngForm">
          <div class="form-group">
            <label for="name">Full Name</label>
            <input type="text" id="name" [(ngModel)]="userData.name" name="name" required>
          </div>
          
          <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" [(ngModel)]="userData.email" name="email" required>
          </div>

          <div class="form-group">
            <label for="password">Password</label>
            <input 
              type="password" 
              id="password" 
              [(ngModel)]="userData.password" 
              name="password" 
              required
              (input)="validatePasswords()">
          </div>

          <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input 
              type="password" 
              id="confirmPassword" 
              [(ngModel)]="confirmPassword" 
              name="confirmPassword" 
              required
              (input)="validatePasswords()">
            <div *ngIf="passwordError" class="password-error">
              {{ passwordError }}
            </div>
          </div>

          <div class="form-group">
            <label for="role">Account Type</label>
            <select 
              id="role" 
              [(ngModel)]="userData.role" 
              name="role" 
              required>
              <option *ngFor="let role of roles" [value]="role.value">
                {{role.label}}
              </option>
            </select>
          </div>

          <div *ngIf="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>

          <button type="submit" [disabled]="!registerForm.form.valid || isLoading || passwordError">
            {{ isLoading ? 'Registering...' : 'Register' }}
          </button>
        </form>
        
        <div class="login-link">
          <p>Already have an account? <a routerLink="/login">Sign In</a></p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .register-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background: linear-gradient(135deg, #5c16c5 0%, #ff4081 100%);
      padding: 20px;
    }
    .register-card {
      background: white;
      padding: 2rem;
      border-radius: 8px;
      width: 100%;
      max-width: 400px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    .form-group {
      margin-bottom: 1rem;
    }
    .form-group label {
      display: block;
      margin-bottom: 0.5rem;
      font-weight: 500;
    }
    .form-group input,
    .form-group select {
      width: 100%;
      padding: 0.75rem;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-size: 1rem;
    }
    .error-message {
      color: #dc3545;
      margin: 1rem 0;
      padding: 0.5rem;
      background-color: #f8d7da;
      border-radius: 4px;
    }
    .password-error {
      color: #dc3545;
      font-size: 0.875rem;
      margin-top: 0.25rem;
    }
    button {
      width: 100%;
      padding: 0.75rem;
      background: #5c16c5;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 1rem;
      font-weight: 500;
      transition: background-color 0.2s;
    }
    button:hover:not(:disabled) {
      background: #4a1099;
    }
    button:disabled {
      background: #ccc;
      cursor: not-allowed;
    }
    .login-link {
      text-align: center;
      margin-top: 1.5rem;
    }
    .login-link a {
      color: #5c16c5;
      text-decoration: none;
      font-weight: 500;
    }
    .login-link a:hover {
      text-decoration: underline;
    }
  `]
})
export class RegisterComponent {
  userData = {
    name: '',
    email: '',
    password: '',
    role: 'USER', // Default role
    phoneNumber: ''
  };

  confirmPassword: string = '';
  passwordError: string = '';

  roles = [
    { value: 'USER', label: 'Regular User' },
    { value: 'ORGANIZER', label: 'Event Organizer' },
    { value: 'ADMIN', label: 'Administrator' }
  ];
  errorMessage = '';
  isLoading = false;

  constructor(private router: Router) {}

  validatePasswords() {
    if (this.userData.password && this.confirmPassword) {
      if (this.userData.password !== this.confirmPassword) {
        this.passwordError = 'Passwords do not match';
      } else if (this.userData.password.length < 6) {
        this.passwordError = 'Password must be at least 6 characters long';
      } else {
        this.passwordError = '';
      }
    } else {
      this.passwordError = '';
    }
  }

  async onSubmit() {
    if (!this.userData.name || !this.userData.email || !this.userData.password) {
      this.errorMessage = 'Please fill in all required fields';
      return;
    }

    if (this.userData.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    try {
      const response = await fetch('http://localhost:8080/api/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*'
        },
        body: JSON.stringify(this.userData)
      });

      if (response.ok) {
        // Registration successful, redirect to login
        this.router.navigate(['/login']);
      } else {
        const errorData = await response.text();
        this.errorMessage = errorData || 'Registration failed. Please try again.';
      }
    } catch (error) {
      console.error('Registration error:', error);
      this.errorMessage = 'An error occurred during registration. Please try again.';
    } finally {
      this.isLoading = false;
    }
  }
}