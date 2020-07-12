import { Component, OnInit, Injectable } from '@angular/core';

@Component({
  selector: 'app-rent-request-details',
  templateUrl: './rent-request-details.component.html',
  styleUrls: ['./rent-request-details.component.css']
})

@Injectable({
  providedIn: 'root'
})
export class RentRequestDetailsComponent implements OnInit {

  request : RentRequestDetailsDTO;

  constructor() { }

  ngOnInit(): void {
  }

  public getRequest(){
    return this.request;
  }

  public setRequest(r : RentRequestDetailsDTO){
    this.request = r;
  }


}
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
  description : string;
  carLocation : string;
};


