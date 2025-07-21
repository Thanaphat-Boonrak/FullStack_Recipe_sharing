import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { LoginRequest } from '../model/userLogin';
import { registerRequest } from '../model/userRegister';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080';
  private http = inject(HttpClient);
  isLogin = signal<boolean>(false);

  constructor() {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.isLogin.set(true);
    }
  }

  LoginAuth(RequestUser: LoginRequest) {
    return this.http.post<any>(`${this.baseUrl}/auth/login`, RequestUser);
  }

  RegisterAuth(RequestUser: registerRequest) {
    return this.http.post<any>(`${this.baseUrl}/auth/register`, RequestUser);
  }

  GetUserProfile() {
    return this.http.get<any>(`${this.baseUrl}/api/user/profile`);
  }

  logout() {
    this.isLogin.set(false);
    localStorage.clear();
  }
}
