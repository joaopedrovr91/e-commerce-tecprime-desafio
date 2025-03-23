import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth/auth.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerObj: any = {
    username: '',
    password: '',
    email: '',
    name: '',
    phone: '',
    city: '',
    street: '',
    number: null,
    zipcode: ''
  };
  registerError: string = '';

  constructor(private authService: AuthService, private router: Router, private http: HttpClient) { }

  register() {
    this.http.post<any>('http://localhost:8080/auth/register', this.registerObj).subscribe(
      (response) => {
        console.log(response);
        this.router.navigate(['/login']);  // Redirect to login after successful registration
      },
      (error) => {
        console.error(error);
        this.registerError = 'Registration failed. Please check your details and try again.';
      }
    );
  }
}