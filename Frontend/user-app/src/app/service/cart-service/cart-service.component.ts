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
  
  constructor() {
   }

  ngOnInit(): void {
  }

  public getAdvertNumber(){
    return JSON.parse(localStorage.getItem('advertNumber'));
  }

  public setAdvertNumber(n : number){
    localStorage.setItem('advertNumber',JSON.stringify(n));
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
