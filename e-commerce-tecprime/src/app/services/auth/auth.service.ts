import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isLoggedInValue: boolean = false;
  private user: any = null;  // Store user information

  constructor(private router: Router) {
    this.loadUserFromLocalStorage();
  }

  login(user: any, returnUrl?: string) {
    localStorage.setItem('user', JSON.stringify(user)); // Store the entire user object
    this.user = user;
    this.isLoggedInValue = true;
    this.router.navigate([returnUrl || '/']);
  }

  logout() {
    localStorage.removeItem('user');
    this.user = null;
    this.isLoggedInValue = false;
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return this.isLoggedInValue;
  }

  getUser(): any {
    return this.user;
  }

  private loadUserFromLocalStorage() {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
      this.isLoggedInValue = true;
    }
  }
}