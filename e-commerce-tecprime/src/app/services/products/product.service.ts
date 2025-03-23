import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Constant } from '../constant/constant';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http:HttpClient) { }

  getAllProducts(page: number, size: number) { // Adicionando parâmetros page e size
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get(Constant.API_END_POINT + Constant.METHODS.GET_ALL_PRODUCTS, { params: params });  // Adicionando os parâmetros à requisição
  }
}
