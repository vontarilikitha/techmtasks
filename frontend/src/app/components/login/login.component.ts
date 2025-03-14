import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  template: `
    <div class="login-container">
      <div class="login-card">
        <h2>Welcome Back</h2>
        <form (ngSubmit)="onSubmit()" #loginForm="ngForm">
          <div class="form-group">
            <label for="email">Email</label>
            <input 
              type="email" 
              id="email" 
              [(ngModel)]="email" 
              name="email" 
              required>
          </div>
          
          <div class="form-group">
            <label for="password">Password</label>
            <input 
              type="password" 
              id="password" 
              [(ngModel)]="password" 
              name="password" 
              required>
          </div>

          <div *ngIf="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>

          <button type="submit" [disabled]="isLoading || !loginForm.form.valid">
            {{ isLoading ? 'Signing in...' : 'Sign In' }}
          </button>
        </form>
        
        <div class="register-link">
          <p>Don't have an account? <a routerLink="/register">Register Now</a></p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .login-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background: linear-gradient(135deg, #5c16c5 0%, #ff4081 100%);
      padding: 20px;
    }
    .login-card {
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
    .form-group input {
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
    .register-link {
      text-align: center;
      margin-top: 1.5rem;
    }
    .register-link a {
      color: #5c16c5;
      text-decoration: none;
      font-weight: 500;
    }
    .register-link a:hover {
      text-decoration: underline;
    }
  `]
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  async onSubmit() {
    if (!this.email || !this.password) {
      this.errorMessage = 'Please enter both email and password';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    try {
      const response = await fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: this.email,
          password: this.password
        })
      });

      const data = await response.json();
      console.log('Login response:', data);

      if (response.ok && data.token) {
        localStorage.setItem('token', data.token);
        localStorage.setItem('user', JSON.stringify(data.user));
        localStorage.setItem('userId', data.user.id.toString()); // Add this line
        
        const userRole = data.user?.role || 'USER';
        switch (userRole) {
          case 'ADMIN':
            await this.router.navigate(['/admin-dashboard']);
            break;
          case 'ORGANIZER':
            await this.router.navigate(['/organizer-dashboard']);
            break;
          default:
            await this.router.navigate(['/shows']);
        }
      } else {
        this.errorMessage = data.message || 'Invalid email or password';
      }
    } catch (error) {
      console.error('Login error:', error);
      this.errorMessage = 'Invalid email or password. Please check your credentials.';
    } finally {
      this.isLoading = false;
    }
}

// In your login component or auth service
async login(credentials: any) {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials)
  });

  if (response.ok) {
    const data = await response.json();
    localStorage.setItem('token', data.token);
    localStorage.setItem('userId', data.userId); // Make sure this is set
  }
}}