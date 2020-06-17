import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  min = Date();
  rentSpan: RentSpan = {
    rentSpan: [new Date()]
  };

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
    //console.log(this.query);
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