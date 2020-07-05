import { Component, OnInit, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  loggedIn: boolean = false;
  noAdverts: boolean = false;
  adverts: Advert[];
  imageURL: string;
  response: Response;

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
    from: new Date(),
    until: new Date()
  };

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    let status = localStorage.getItem('loggedIn');
    status === 'true' ? this.loggedIn = true : this.loggedIn = false;
    this.fetchAdverts();
  }

  async fetchAdverts() {
    const apiEndpoint = 'http://localhost:8080/search-service/advert';
    this.http.get(apiEndpoint).subscribe(response => {
        this.adverts = response as Array<Advert>;
        if(this.adverts.length === 0) {
          this.noAdverts = true;
        } else {
          this.noAdverts = false;
        }
        this.assignImagesToAdverts(this.adverts);
    }, err => {
      console.log('Unable to fetch adverts: ', err);
    });
  }

  async assignImagesToAdverts(adverts : Array<Advert>) {
    for(let i=0; i<adverts.length; i++) {
      this.getImageURL(this.adverts[i]);
    }
  }

  formatAdvertDate(givenDate: Date) {
    let date = new Date(givenDate);
    return (date.getUTCDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear() + ".");
  }

  async getImageURL(advert: Advert) {
    const apiEndpoint = 'http://localhost:8080/image-service/profile-image/' + advert.uuid;
    this.http.get(apiEndpoint, {responseType: 'text', withCredentials: true}).subscribe(response => {
      advert.imageURL = response as string;
    }, err => {
      console.log('Error fetching image URL', err);
    });
  }

  onSearch() {
    this.query.from = this.rentSpan.rentSpan[0];
    this.query.until = this.rentSpan.rentSpan[1];
    
    const apiEndpoint = 'http://localhost:8080/search-service/filter';
    this.http.post(apiEndpoint, this.query).subscribe(res => {
      this.adverts = res as Array<Advert>;
      this.assignImagesToAdverts(this.adverts);
      if(this.adverts.length === 0) {
        this.noAdverts = true;
      } else {
        this.noAdverts = false;
      }
    }, err => {
      console.log('Error filtering adverts', err);
    });
    
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
    return this.query.city.length > 0 && this.query.until > this.query.from && this.query.from > new Date();
  }

  routToDetails(advert: Advert) {
    localStorage.setItem('advertUUID', advert.uuid);
    this.router.navigateByUrl("/details");
  }

}

export interface Query {
  city: string;
  from: Date;
  until: Date;
};

interface City {
  value: string;
  viewValue: string;
};

interface RentSpan {
  rentSpan: Date[];
};

export interface Advert {
  publisher: string;
  price: number;
  carLocation: string;
  availableForRentFrom: Date;
  availableForRentUntil: Date;
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