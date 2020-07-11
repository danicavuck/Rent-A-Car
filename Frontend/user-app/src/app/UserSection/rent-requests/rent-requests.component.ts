import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { RentRequestDetailsComponent } from 'src/app/service/rent-request-details/rent-request-details.component';

@Component({
  selector: 'app-rent-requests',
  templateUrl: './rent-requests.component.html',
  styleUrls: ['./rent-requests.component.css']
})
export class RentRequestsComponent implements OnInit {

  public displayColumns: string[] = ['username', 'bundle', 'dateStart','timeStart', 'dateEnd','timeEnd', 'actions'];
  public model : Array<RentRequestDetailsDTO>;

  constructor(private router:Router,private http: HttpClient, private service : RentRequestDetailsComponent) { 
    //this.test();
    this.getRequests();
  }

  ngOnInit(): void {
  }
  
  async getRequests() {
    let username:string = localStorage.getItem('username');
    console.log(username);
    const apiEndpoint = 'http://localhost:8080/renting-service/rentRequest/'+ username;

    this.http.get(apiEndpoint).subscribe(response => {
      this.model = response as Array<RentRequestDetailsDTO>;
      console.log(this.model);
      console.log(response);
    }, err => {
      console.log('Unable to fetch rent requests',err);
    });
  }

  async test() {
    const apiEndpoint = 'http://localhost:8080/renting-service/rentRequest/test';
    this.http.get(apiEndpoint).subscribe(response => {
     
        console.log("pogodio");
    }, err => {
      console.log('nie pogodio ', err);
    });
  }

  onDetails(request : RentRequestDetailsDTO) {
    this.service.setRequest(request);
    this.router.navigateByUrl('/rent-requests/details');
  }

  onAccept(uuid : string) {
    let username : string = localStorage.getItem('username')
    const apiEndpoint = 'http://localhost:8080/renting-service/rentRequest/accept/'+ username;
    this.http.post(apiEndpoint, uuid, {responseType: 'text', withCredentials: true}).subscribe(response => {
      this.getRequests();
    }, err => {
      console.log('Unable to accept request', err);
    });
  }

  onDecline(uuid: string) {
    let username : string = localStorage.getItem('username')
    const apiEndpoint = 'http://localhost:8080/renting-service/rentRequest/decline/'+ username;;
    this.http.post(apiEndpoint, uuid, {responseType: 'text', withCredentials: true}).subscribe(response => {
      this.getRequests();
    }, err => {
      console.log('Unable to decline request: ', uuid);
    });
  }

  onLogout() {
    const apiEndpoint = 'http://localhost:8080/auth-service/logout';
    this.http.post(apiEndpoint, {responseType: 'json', withCredentials: true}).subscribe(data => {
      localStorage.clear();
      this.router.navigateByUrl('/login');
    }, err => {
      console.log('Unable to log out');
    });
  }


}
export interface RentRequestDetailsDTO {
  uuid : string;
  username: string;
  bundle : boolean;
  timeStart : string;
  timeEnd : string;
  dateStart: string;
  dateEnd: string;
  advertsUUIDs : string[];
};

