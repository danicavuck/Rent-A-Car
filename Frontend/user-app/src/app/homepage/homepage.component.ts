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
  imageURL: string;
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
    this.getImageURL('75fb7c38d68644fc8c9c156161e5697f');
  }

  async fetchAdverts() {
    const apiEndpoint = 'http://localhost:8080/posting-service/advert';
    this.http.get(apiEndpoint).subscribe(response => {
        this.adverts = response as Array<Advert>;
        console.log(this.adverts);
    }, err => {
      console.log('Unable to fetch adverts: ', err);
    });
  }

  formatAdvertDate(givenDate: Date) {
    let date = new Date(givenDate);
    return (date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear() + ".");
  }


  async getImageURL(advertUuid: string) {
    const apiEndpoint = 'http://localhost:8080/posting-service/advert/profile-image/' + advertUuid;
    this.http.get(apiEndpoint, {responseType: 'text', withCredentials: true}).subscribe(response => {
      this.imageURL = response;
    }, err => {
      console.log('Error fetching image URL', err);
    });
  }

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