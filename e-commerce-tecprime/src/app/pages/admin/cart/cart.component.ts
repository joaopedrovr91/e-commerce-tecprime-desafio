import { Component, OnInit, OnDestroy } from '@angular/core';
import { CartService } from '../../../services/cart/cart.service';
import { CartItem } from '../../../models/cart-item.model';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit, OnDestroy {

  cartItems: CartItem[] = [];
  private cartSubscription: Subscription | undefined;

  constructor(
    private cartService: CartService,
    private router: Router,
    private authService: AuthService 
  ) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();

    this.cartSubscription = this.cartService.cartItems$.subscribe(items => {
      this.cartItems = items;
    });
  }

  ngOnDestroy(): void {
    if (this.cartSubscription) {
      this.cartSubscription.unsubscribe();
    }
  }

  clearCart() {
    this.cartService.clearCart();
  }

  getTotal(): number {
    return this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
  }

  goToCheckout() {
    if (this.authService.isLoggedIn()) {
      // Redirecionar para a tela de pagamento
      this.router.navigate(['/checkout']);
    } else {
      // Redirecionar para a tela de login
      this.router.navigate(['/login']);
    }
  }
}