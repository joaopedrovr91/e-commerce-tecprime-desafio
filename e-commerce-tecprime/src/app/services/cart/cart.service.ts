import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { CartItem } from '../../models/cart-item.model';
import { BehaviorSubject } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartItems: CartItem[] = [];
  private cartItemsSubject = new BehaviorSubject<CartItem[]>(this.cartItems);
  cartItems$ = this.cartItemsSubject.asObservable();

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    if (isPlatformBrowser(this.platformId)) {
      // Carregar o carrinho do localStorage ao inicializar o serviço (opcional)
      const storedCart = localStorage.getItem('cart');
      if (storedCart) {
        this.cartItems = JSON.parse(storedCart);
        this.cartItemsSubject.next(this.cartItems);
      }
    }
  }

  addToCart(product: any) {
    if (isPlatformBrowser(this.platformId)) {
      const existingItem = this.cartItems.find(item => item.productId === product.id);

      if (existingItem) {
        existingItem.quantity++;
      } else {
        const cartItem: CartItem = {
          productId: product.id,
          title: product.title,
          price: product.price,
          image: product.image,
          quantity: 1
        };
        this.cartItems.push(cartItem);
      }

      this.cartItemsSubject.next([...this.cartItems]); // Notifica os subscribers
      this.saveCart(); // Salva o carrinho no localStorage
    }
  }

  getCartItems(): CartItem[] {
    return this.cartItems;
  }

  clearCart() {
    if (isPlatformBrowser(this.platformId)) {
      this.cartItems = [];
      this.cartItemsSubject.next([...this.cartItems]);
      this.saveCart();
    }
  }

  private saveCart() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('cart', JSON.stringify(this.cartItems));
    }
  }
}