import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CartService } from '../../../services/cart/cart.service';
import { CartComponent } from '../cart/cart.component';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, CommonModule, RouterLink],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent {
  cartItemsCount$: Observable<number>;

  constructor(private cartService: CartService,
    public authService: AuthService, private router: Router) {
    this.cartItemsCount$ = this.cartService.cartItems$.pipe(
      map(items => items.reduce((acc, item) => acc + item.quantity, 0))
    );
  }

  logout() {
    this.authService.logout();
  }

  getUserName(): string {
    const user = this.authService.getUser();
    return user ? user.name : '';  // Assuming the user object has a 'name' property
  }
}