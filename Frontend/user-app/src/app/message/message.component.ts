import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
  model : Message = {
    senderUsername: '',
    receiverUsername: '',
    text : ''
  };
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private http: HttpClient) {
    this.model.receiverUsername = this.data.receiverUsername;
    this.model.senderUsername = this.data.senderUsername;
   }

  ngOnInit(): void {
  }

  async onSubmit() {
    const apiEndpoint = 'http://localhost:8080/messaging-service';
    this.http.post(apiEndpoint, this.model).subscribe(res => {
      console.log('Message successfully sent');
    }, err => {
      console.log('Unable to send message');
    });
  }

}

interface Message {
  senderUsername : string;
  receiverUsername : string;
  text : string;
}
