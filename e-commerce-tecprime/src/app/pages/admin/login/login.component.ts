import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth/auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginObj: any = {
    username: '',
    password: ''
  };
  loginError: string = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  login() {
    this.http.post<any>('http://localhost:8080/auth/login', this.loginObj).subscribe(
      (response) => {
        // Assuming the server returns the entire user object
        this.authService.login(response, this.route.snapshot.queryParams['returnUrl'] || '/'); // Pass the user object
      },
      (error) => {
        this.loginError = 'Invalid credentials. Please try again.';
      }
    );
  }
}