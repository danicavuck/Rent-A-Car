import { Component, OnInit } from '@angular/core';
import { InboxContactsComponent } from '../inbox-contacts/inbox-contacts.component';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css'],
})
export class InboxComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

  onLogout() {
    const apiEndpoint = 'http://localhost:8080/auth-service/logout';

    this.http.post(apiEndpoint, '', {responseType: 'json',withCredentials: true}).subscribe(() => {
      localStorage.clear();
      this.router.navigateByUrl('/home');
    }, err => {
      console.log('Unable to log out', err);
    });
  }

}
