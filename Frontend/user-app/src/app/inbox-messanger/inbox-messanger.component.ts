import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-inbox-messanger',
  templateUrl: './inbox-messanger.component.html',
  styleUrls: ['./inbox-messanger.component.css']
})
export class InboxMessangerComponent implements OnInit {
  messages : Array<Message>;
  replyMessage : string = '';
  renderedContent : string;
  msgReply : Message = {
    senderUsername : '',
    receiverUsername : '',
    text : '',
    timestamp : new Date()
  };

  constructor(private http: HttpClient) {
    
  }

  ngOnInit(): void {
  }

  async fetchMessages() {
    let username1 = localStorage.getItem('username');
    username1.replace(' ', '%20');
    let username2 = localStorage.getItem('chatWith');
    username2.replace(' ', '%20');
    const apiEndpoit = 'http://localhost:8080/messaging-service/chat/' + username1 + '/' + username2;

    this.http.get(apiEndpoit).subscribe(res => {
      this.messages = res as Array<Message>;
      this.renderContent();
    }, err => {
      console.log(`Unable to fetch messages betwwen ${username1} and ${username2}`);
    });
  }

  formatAdvertDate(givenDate: Date) {
    var weekday = new Array(7);
    weekday[0] = "Sun";
    weekday[1] = "Mon";
    weekday[2] = "Tue";
    weekday[3] = "Wed";
    weekday[4] = "Thu";
    weekday[5] = "Fri";
    weekday[6] = "Sat";
    let date = new Date(givenDate);
    return (`\tSent: ${date.getHours()}:${date.getMinutes()} on ${date.getUTCDate()}.${date.getMonth()+1}.${date.getFullYear()}.\n\n`);
  }

  async renderContent() {
    this.renderedContent = '\n';
    for(let i=0; i<this.messages.length; i++) {
      this.renderedContent += `${this.messages[i].senderUsername}: ${this.messages[i].text} \n`;
      this.renderedContent += `${this.formatAdvertDate(this.messages[i].timestamp)}`;
    }
  }

  async onSendMessage() {
    let sender = localStorage.getItem('username');
    let receiver = localStorage.getItem('chatWith');
    this.msgReply.senderUsername = sender;
    this.msgReply.receiverUsername = receiver;
    this.msgReply.text = this.replyMessage;

    sender.replace(' ', '%20');
    receiver.replace(' ', '%20');
    if(this.msgReply.text !== '') {
      const apiEndpoint ='http://localhost:8080/messaging-service';
      this.http.post(apiEndpoint, this.msgReply).subscribe( res => {
        console.log('Message sent successfully');
        this.replyMessage = '';
        this.fetchMessages();
      }, err => {
        console.log('Unable to send a message');
      });
    } else {
      alert('Message cannot be empty');
    }

  }

}

interface Message {
  senderUsername : string;
  receiverUsername : string;
  text : string;
  timestamp : Date;
}