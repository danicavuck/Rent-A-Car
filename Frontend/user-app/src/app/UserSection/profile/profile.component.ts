import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MessageComponent } from '../../message/message.component'

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  private providedUsername : string;
  user : User;
  avatarURL : string = '';
  adverts : Advert[];
  noAdverts : boolean = false;

  constructor(private http: HttpClient, private router: Router, public dialog: MatDialog) {
    this.providedUsername = localStorage.getItem('userDetails');
    this.fetchUserInfo(this.providedUsername);
    this.fetchAvatar();
    this.fetchAdverts();
   }

  ngOnInit(): void {
  }

  openDialog() {
    let senderUsername = localStorage.getItem('username');
    let receiverUsername = this.providedUsername;
    let dialogRef = this.dialog.open(MessageComponent, {
      data: {senderUsername: senderUsername, receiverUsername: receiverUsername},
      height: '300px',
      width: '500px',
    });
  }

  async fetchUserInfo(username: string) {
    const apiEndpoint = 'http://localhost:8080/posting-service/user/' + username;
    this.http.get(apiEndpoint).subscribe(response => {
      this.user = response as User;
    }, err => {
      console.log('Error fetching user details ' + err);
    });
  }

  async fetchAvatar() {
    const apiEndpoint = 'http://localhost:8080/image-service/profile-image/user';
    this.http.get(apiEndpoint, {responseType: 'text'}).subscribe(response => {
      this.avatarURL = response as string;
    }, err => {
      console.log('Unable to fetch avatar', err);
    });
  }

  async fetchAdverts() {
    let username = this.providedUsername.replace(' ', '%20');
    const apiEndpoint = 'http://localhost:8080/search-service/advert/user/' + username;
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

  async getImageURL(advert: Advert) {
    const apiEndpoint = 'http://localhost:8080/image-service/profile-image/' + advert.uuid;
    this.http.get(apiEndpoint, {responseType: 'text', withCredentials: true}).subscribe(response => {
      advert.imageURL = response as string;
    }, err => {
      console.log('Error fetching image URL', err);
    });
  }

  routToDetails(advert: Advert) {
    localStorage.setItem('advertUUID', advert.uuid);
    this.router.navigateByUrl("/details");
  }

  formatAdvertDate(givenDate: Date) {
    let date = new Date(givenDate);
    return (date.getUTCDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear() + ".");
  }

  async onSendMessage() {

  }

}

interface User {
  username : string;
  firstName : string;
  lastName : string;
  email : string;
  address : string;
  numberOfAdvertsCancelled : number;
};

interface Advert {
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
