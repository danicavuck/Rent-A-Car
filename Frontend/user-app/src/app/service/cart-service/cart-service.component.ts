import { Component, OnInit, Injectable } from '@angular/core';

@Component({
  selector: 'app-cart-service',
  templateUrl: './cart-service.component.html',
  styleUrls: ['./cart-service.component.css']
})

@Injectable({
  providedIn: 'root'
})
export class CartServiceComponent implements OnInit {
  private advert_number : number;
  
  constructor() {
   }

  ngOnInit(): void {
  }

  public getAdvertNumber(){
    return this.advert_number;
  }

  public setAdvertNumber(n : number){
    this.advert_number = n;
  }

  public addAdvertsToCart(uuids:string[]){
    localStorage.setItem("adverts",JSON.stringify(uuids));
  }

  public getFromCart(){
    return JSON.parse(localStorage.getItem('adverts'));
  }

  public removeAllAdverts(){
    return localStorage.removeItem("adverts");
  }


}
