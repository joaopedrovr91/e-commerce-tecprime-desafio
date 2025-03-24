import { Component } from '@angular/core';
import { CartService } from '../../services/cart/cart.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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

  constructor(private cartService: CartService, private router: Router, private http: HttpClient) { }

  finalizePurchase() {
    const orderData = {
      order: {
        userId: 1, //  Pegue o ID do usuário do serviço de autenticação ou de onde você o armazenou
        status: "pendente", // ou outro status inicial
        shippingName: this.name,
        shippingEmail: this.email,
        shippingAddress: this.address,
        paymentMethod: this.paymentMethod
      },
      orderItems: this.cartService.getCartItems().map(item => ({
        productId: item.productId,
        quantity: item.quantity,
        price: item.price
      }))
    };
    
     // Defina o cabeçalho Content-Type
     const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    this.http.post('http://localhost:8080/orders', orderData, { headers: headers }).subscribe(
      (response) => {
        alert('Compra finalizada com sucesso!');
        this.cartService.clearCart();
        this.router.navigate(['/']);
      },
      (error) => {
        console.error('Erro ao finalizar a compra:', error);
        alert('Ocorreu um erro ao finalizar a compra.');
      }
    );
  }
}