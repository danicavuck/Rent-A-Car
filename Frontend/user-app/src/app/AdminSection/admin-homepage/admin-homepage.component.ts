import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-homepage',
  templateUrl: './admin-homepage.component.html',
  styleUrls: ['./admin-homepage.component.css']
})
export class AdminHomepageComponent implements OnInit {
  public model: Array<User>;
  private user: User = {
    username: '',
    firstName: '',
    lastName: '',
    address: '',
    email: '',
    numberOfAdvertsCancelled: 0
  };

  public displayColumns: string[] = ['username', 'firstName', 'lastName', 'email', 'numberOfAdvertsCancelled', 'actions'];

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.getUsers();
  }

  async getUsers() {
    const apiEndpoint = 'http://localhost:8080/admin-service/user';

    this.http.get(apiEndpoint).subscribe(response => {
      this.model = response as Array<User>;
    }, err => {
      console.log('Unable to fetch users');
    });
  }

  onLogout() {
    const apiEndpoint = 'http://localhost:8080/auth-service/logout';
    this.http.post(apiEndpoint, {responseType: 'json', withCredentials: true}).subscribe(data => {
      localStorage.removeItem('username');
      this.router.navigateByUrl('/login');
    }, err => {
      console.log('Unable to log out');
    });
  }

  onBlock(chosenUser: User) {
    const apiEndpoint = 'http://localhost:8080/admin-service/user/block';
    this.http.post(apiEndpoint, chosenUser, {responseType: 'text', withCredentials: true}).subscribe(response => {
      console.log(response);
    }, err => {
      console.log('Unable to deactive user: ', chosenUser);
    });
  }

}

export interface User {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  address: string;
  numberOfAdvertsCancelled: number;
};
