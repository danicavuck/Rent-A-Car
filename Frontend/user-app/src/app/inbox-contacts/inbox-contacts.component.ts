import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-inbox-contacts',
  templateUrl: './inbox-contacts.component.html',
  styleUrls: ['./inbox-contacts.component.css']
})
export class InboxContactsComponent implements OnInit {

  username : string;
  contacts: string[];

  constructor(private http: HttpClient) { 
    this.username = localStorage.getItem('username');
    this.fetchContancts(this.username);
  }

  ngOnInit(): void {
  }

  async fetchContancts(username : string) {
    const apiEndpoint = 'http://localhost:8080/messaging-service/chat/' + username;
    this.http.get(apiEndpoint).subscribe(res => {
      this.contacts = res as string[];
    }, err => {
      console.log('Error fetching contacts');
    });
  }

  async onChatSelected(username : string) {
    localStorage.setItem('chatWith', username);
  }
  
}
