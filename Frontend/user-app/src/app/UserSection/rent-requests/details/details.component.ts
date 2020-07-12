import { Component, OnInit } from '@angular/core';
import { RentRequestDetailsComponent } from 'src/app/service/rent-request-details/rent-request-details.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  adverts: Advert[];
  request : RentRequestDetailsDTO;

  constructor(private http : HttpClient,private service : RentRequestDetailsComponent) { 
    this.request = service.getRequest();
  }

  ngOnInit(): void {

    this.fetchAdverts(this.request);
  }

  async fetchAdverts(request : RentRequestDetailsDTO) {
    const apiEndpoint = 'http://localhost:8080/posting-service/fromRentRequest';
    this.http.post(apiEndpoint,request).subscribe(response => {
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
  imageURL: string;
};

export interface RentRequestDetailsDTO {
  uuid : string;
  requestUsername: string;
  publisherUsername : string;
  bundle : boolean;
  timeStart : string;
  timeEnd : string;
  dateStart: string;
  dateEnd: string;
  advertsUUIDs : string[];
};

