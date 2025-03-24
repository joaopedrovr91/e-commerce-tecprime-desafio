import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
  })
  export class AuthService {
  
    private baseUrl = 'http://localhost:8080';
  
    constructor(private http: HttpClient, private router: Router) { }
  
    login(token: string): void {
      if (typeof window !== 'undefined') { // Verifica se estamos no navegador
        localStorage.setItem('token', token);
      }
    }
  
    logout(): void {
      if (typeof window !== 'undefined') { // Verifica se estamos no navegador
        localStorage.removeItem('token');
      }
    }
  
    isLoggedIn(): boolean {
      if (typeof window !== 'undefined') { // Verifica se estamos no navegador
        return !!localStorage.getItem('token'); // Use !! para converter para boolean
      }
      return false; // Retorna false se não estiver no navegador
    }
  
    getToken(): string | null {
      if (typeof window !== 'undefined') { // Verifica se estamos no navegador
        return localStorage.getItem('token');
      }
      return null;
    }
  }