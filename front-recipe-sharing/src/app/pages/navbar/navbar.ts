import { Component, inject, OnInit, signal } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../service/auth-service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-navbar',
  imports: [MatToolbarModule, MatIconModule, MatButtonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar implements OnInit {
  authService = inject(AuthService);
  private router = inject(Router);
  name = signal<string | null>('');

  ngOnInit(): void {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.authService.GetUserProfile().subscribe({
        next: (res) => {
          this.name.set(res.name);
        },
        error: (err) => {
          console.error('Get profile error', err);
          this.authService.logout();
          this.router.navigate(['/auth']);
        },
      });
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth']);
  }
}
