import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../service/auth-service';
import { Router } from '@angular/router';
import { registerRequest } from '../../model/userRegister';
import { LoginRequest } from '../../model/userLogin';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [ReactiveFormsModule, MatButtonModule, MatInputModule],
  templateUrl: './auth.html',
  styleUrl: './auth.css',
})
export class Auth {
  isLogin = false;
  loginError: string | null = null;

  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  registerform = this.fb.group({
    fullname: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  loginform = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });

  handleRegister() {
    const fullNameSplit =
      this.registerform.value.fullname?.trim().split(' ') || [];
    const firstName = fullNameSplit[0] || '';
    const lastName = fullNameSplit[1] || '';

    const payload: registerRequest = {
      name: firstName,
      surname: lastName || '',
      email: this.registerform.value.email!,
      password: this.registerform.value.password!,
    };

    this.authService.RegisterAuth(payload).subscribe({
      next: (res) => {
        localStorage.setItem('accessToken', res.token);
        this.router.navigate(['/home']);
        this.authService.isLogin.set(true);
      },
      error: (err) => {
        console.error('Register Error', err);
      },
    });
  }

  handleLogin() {
    this.loginError = null;
    const payload: LoginRequest = {
      email: this.loginform.value.email!,
      password: this.loginform.value.password!,
    };

    this.authService.LoginAuth(payload).subscribe({
      next: (res) => {
        localStorage.setItem('accessToken', res.token);
        this.router.navigate(['/home']);
        this.authService.isLogin.set(true);
      },
      error: (err) => {
        console.error('Login Error', err);
        if (err.status === 403 || err.status === 401) {
          this.loginError = 'Invalid email or password';
        } else {
          this.loginError = 'Something went wrong, please try again';
        }
      },
    });
  }

  isInvalidRegister(controlName: string): boolean {
    const control = this.registerform.get(controlName);
    return !!(control && control.invalid && (control.dirty || control.touched));
  }

  isInvalidLogin(controlName: string): boolean {
    const control = this.loginform.get(controlName);
    return !!(control && control.invalid && (control.dirty || control.touched));
  }

  toggleMode() {
    this.isLogin = !this.isLogin;
    this.loginError = null;
  }
}
