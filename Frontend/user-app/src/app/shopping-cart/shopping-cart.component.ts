import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { CartServiceComponent } from '../service/cart-service/cart-service.component';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  constructor(private router:Router,private http: HttpClient,private cartService : CartServiceComponent) {
    this.bundle = false;
    this.getCartInfo();  
   }

  adverts : Advert[];
  advertUUIDS : string[];
  adverts_number : number;
  isEmpty : boolean;
  totalPrice = 0;
  temp : Advert;
  bundle : boolean;
  
  ngOnInit(): void {
    if(this.adverts_number != 0)
    {
      console.log("Cart not empty!");
      this.isEmpty = false;
      console.log(this.advertUUIDS);
      this.fetchAdverts(this.advertUUIDS);

    }
    else
    {
      console.log("Cart is empty!");
      this.isEmpty = true;
    }

  }

  getCartInfo() : void {
    this.adverts_number = this.cartService.getAdvertNumber();
    this.advertUUIDS = this.cartService.getFromCart();
  }

  onRemoveFromCart(uuid : string) : void{
    const index: number = this.advertUUIDS.indexOf(uuid);
    if (index !== -1) {
        this.adverts.splice(index,1);
        this.advertUUIDS.splice(index, 1);
        console.log(this.advertUUIDS);
        this.totalPrice = 0;
        this.adverts_number-=1;
        this.cartService.setAdvertNumber(this.adverts_number);
        this.cartService.addAdvertsToCart(this.advertUUIDS);
        if(this.adverts_number != 0)
        {
          this.fetchAdverts(this.advertUUIDS);
        }

    }        
  }

  async onCheckout(){
    const apiEndpoint = 'http://localhost:8080/renting-service/rentRequest/add';
    let rentRequest : RentRequestDTO = {
      advertIds : this.advertUUIDS,
      bundle : this.bundle,
      username : localStorage.getItem('username')
    }
    console.log(rentRequest);

    this.http.post(apiEndpoint,rentRequest,{responseType: "text"}).subscribe(response => {
        console.log(response);
        this.router.navigateByUrl("/home");
    }, err => {
      console.log('Unable make rent request: ', err);
    });
    
  }
  
  async fetchAdverts(advert_uuids) {
    const apiEndpoint = 'http://localhost:8080/posting-service/advert/advertList';
      this.http.post(apiEndpoint,advert_uuids).subscribe(response => {
        this.adverts = response as Array<Advert>;
        for(let i = 0; i < this.adverts.length; i++) {
              this.totalPrice += this.adverts[i].price;
        }
    }, err => {
      console.log('Unable to fetch adverts: ', err);
    });

  }

}

interface RentRequestDTO{
  advertIds : string[];
  bundle : boolean;
  username : string;
}


interface Advert {
  username: string;
  price: number;
  carLocation: string;
  dateStart: Date;
  dateEnd: Date;
  brand: string;
  model: string;
  fuel: string;
  transmission: string;
  bodyType: string;
  mileage: number;
  isRentLimited: boolean;
  limitInKilometers: number;
  numberOfSeatsForChildren: number;
  uuid: string;
  deleted : boolean;
  imageURL: string;
};
