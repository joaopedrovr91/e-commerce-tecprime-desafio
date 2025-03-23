import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../services/products/product.service';
import { Product } from '../../../models/product.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { tap } from 'rxjs/operators';
import { CartService } from '../../../services/cart/cart.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule,RouterModule ],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {

  productList: Product[] = [];
  private _page = 0;
  private _size = 10;
  totalElements = 0;
  isLastPage = false;

  constructor(
    private productSrv: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private cartService: CartService  // Injetando o CartService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this._page = Number(params['page']) || 0;
      this._size = Number(params['size']) || 10;
      this.loadProducts();
    });
  }

  get page(): number {
    return this._page;
  }

  set page(value: number) {
    this._page = value;
    this.updateRoute();
  }

    get size(): number {
    return this._size;
  }

  set size(value: number) {
    this._size = value;
    this.updateRoute(); 
  }

  loadProducts() {
    this.productSrv.getAllProducts(this.page, this.size).pipe(
      tap(res => console.log('Dados recebidos do backend:', res))
    ).subscribe((res: any) => {
      this.productList = res.content;
      this.totalElements = res.totalElements;
      this.isLastPage = res.last;
      console.log('productList atualizado:', this.productList);
      console.log('isLastPage:', this.isLastPage); 
    });
  }

  nextPage() {
    if (!this.isLastPage) {
      this.page++;
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
    }
  }

  updateRoute() {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        page: this.page,
        size: this.size
      },
      queryParamsHandling: 'merge',
    });
  }

  addToCart(product: Product) { // Mudando o tipo para Product
    this.cartService.addToCart(product);
  }
}