import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  currentRate = 5;

  model: Review = {
    text: '',
    mark: 1,
    username: '',
    uuid: ''
  };

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private http: HttpClient) {

   }

  ngOnInit(): void {
  }

  onSubmit() {
    this.model.mark = this.currentRate;
    this.model.username = this.data.username;
    this.model.uuid = this.data.uuid;

    const apiEndpoint = 'http://localhost:8080/review-service/comment';
    this.http.post(apiEndpoint, this.model, {responseType: 'text', withCredentials: true}).subscribe(() => {
      console.log('Review sent');
    }, err => {
      console.log('Error occurred: ', err);
    });
  }

}

export interface Review {
  text: string;
  mark: number;
  username: string;
  uuid: string;
};
