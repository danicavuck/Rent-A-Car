import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agent-homepage',
  templateUrl: './agent-homepage.component.html',
  styleUrls: ['./agent-homepage.component.css']
})
export class AgentHomepageComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

  onLogout() {
    const apiEndpoint = 'http://localhost:8080/auth-service/logout';
    this.http.post(apiEndpoint, {responseType: 'json', withCredentials: true}).subscribe(data => {
      console.log('Successfully logged out');
      this.router.navigateByUrl('/login');
    }, err => {
      console.log('Unable to log out');
    });
  }

}
