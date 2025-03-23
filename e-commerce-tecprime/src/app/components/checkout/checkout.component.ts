import { Component } from '@angular/core';
import { CartService } from '../../services/cart/cart.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {

  paymentMethod: string = '';
  name: string = '';
  email: string = '';
  address: string = '';

  constructor(private cartService: CartService, private router: Router) { }

  finalizePurchase() {
    // Lógica de simulação de pagamento
    alert('Compra finalizada com sucesso!');
    this.cartService.clearCart();
    this.router.navigate(['/']);
  }
}