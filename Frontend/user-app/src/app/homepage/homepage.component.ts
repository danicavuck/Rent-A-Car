import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';


@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  loggedIn: boolean = false;
  adverts: Advert[];
  imageToShow: any;
  response: Response;
  blob: Blob;

  min = Date();
  rentSpan: RentSpan = {
    rentSpan: [new Date()]
  };

  numberOfAdverts = [2, 2, 3, 4, 5];

  cities : City[] = [
    {value: 'Novi Sad', viewValue: 'Novi Sad'},
    {value: 'Kraljevo', viewValue: 'Kraljevo'},
    {value: 'Nis', viewValue: 'Nis'},
    {value: 'Beograd', viewValue: 'Beograd'}
  ];

  query: Query = {
    city: '',
    rentStartingDate: new Date(),
    rentEndingDate: new Date()
  };

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    let status = localStorage.getItem('loggedIn');
    status === 'true' ? this.loggedIn = true : this.loggedIn = false;
    this.fetchAdverts();
  }

  async fetchAdverts() {
    const apiEndpoint = 'http://localhost:8080/posting-service/advert';
    this.http.get(apiEndpoint).subscribe(response => {
        this.adverts = response as Array<Advert>;
        for(let i = 0; i < this.adverts.length; i++) {
          //this.fetchImage(this.adverts[i]);
        }
        console.log(this.adverts);
    }, err => {
      console.log('Unable to fetch adverts: ', err);
    });
  }

  formatAdvertDate(givenDate: Date) {
    let date = new Date(givenDate);
    return (date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear() + ".");
  }

  // fetchImage(advert: Advert) {
  //   let blob = this.getBlobFromBackend(advert);
  //   setTimeout(function () {
  //     this.createImageFromBlob(blob);   
  //   }, 3000);
  // }

  // getBlobFromBackend(advert: Advert) {
  //   const apiEndpoint = 'http://localhost:8080/posting-service/advert/profile-image/' + advert.uuid;
  //   return this.http.get(apiEndpoint).pipe(
  //     map((res: Response) => res.blob())
  //   );
  // }

  // async createImageFromBlob(image) {
  //   let reader = new FileReader();
  //   reader.addEventListener("load", () => {
  //      this.imageToShow = reader.result;
  //   }, false);
  //   if (image) {
  //      reader.readAsDataURL(image);
  //   }
  // }

  onSearch() {
    this.query.rentStartingDate = this.rentSpan.rentSpan[0];
    this.query.rentEndingDate = this.rentSpan.rentSpan[1];

    if(this.validateInput()) {
      console.log(this.query);
    } else {
      alert('Invalid query');
      console.log(this.query);
    }
  }

  onLogout() {
    const apiEndpoint = 'http://localhost:8080/auth-service/logout';

    this.http.post(apiEndpoint, '', {responseType: 'json',withCredentials: true}).subscribe(() => {
      this.loggedIn = false;
      localStorage.clear();
    }, err => {
      console.log('Unable to log out', err);
    });
  }

  validateInput() {
    return this.query.city.length > 0 && this.query.rentEndingDate > this.query.rentStartingDate && this.query.rentStartingDate > new Date();
  }

}

export interface Query {
  city: string;
  rentStartingDate: Date;
  rentEndingDate: Date;
};

interface City {
  value: string;
  viewValue: string;
};

interface RentSpan {
  rentSpan: Date[];
};

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
  imageURL: string;
};