import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-homepage',
  templateUrl: './admin-homepage.component.html',
  styleUrls: ['./admin-homepage.component.css']
})
export class AdminHomepageComponent implements OnInit {

  comments: Comment[];

  public model: Array<User>;
  private user: User = {
    username: '',
    firstName: '',
    lastName: '',
    address: '',
    email: '',
    isActive: true,
    isBlocked: false,
    numberOfAdvertsCancelled: 0
  };

  public displayColumns: string[] = ['username', 'firstName', 'lastName', 'email', 'numberOfAdvertsCancelled', 'actions'];
  public commentColumns: string[] = ['text', 'username', 'mark', 'actions'];

  constructor(private http: HttpClient, private router: Router) {
    this.getUsers();
    this.getPendingComments();
   }

  ngOnInit(): void {
  }

  async getPendingComments() {
    const apiEndpoint = 'http://localhost:8080/review-service/comment/pending';
    this.http.get(apiEndpoint).subscribe(response => {
      this.comments = response as Array<Comment>;
      console.log(this.comments);
    }, err => {
      console.log('Error occurred while fetching comments');
    });
  }

  async getUsers() {
    const apiEndpoint = 'http://localhost:8080/admin-service/user';

    this.http.get(apiEndpoint).subscribe(response => {
      this.model = response as Array<User>;
      console.log(response);
    }, err => {
      console.log('Unable to fetch users');
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

  onActivate(chosenUser: User) {
    const apiEndpoint = 'http://localhost:8080/admin-service/user/active';
    this.http.post(apiEndpoint, chosenUser, {responseType: 'text', withCredentials: true}).subscribe(response => {
      this.ngOnInit();
    }, err => {
      console.log('Unable to active user', err);
    });
  }

  onDelete(chosenUser: User) {
    const apiEndpoint = 'http://localhost:8080/admin-service/user/delete';
    this.http.post(apiEndpoint, chosenUser, {responseType: 'text', withCredentials: true}).subscribe(response => {
      this.ngOnInit();
    }, err => {
      console.log('Unable to delete user', err);
    });
  }

  onBlock(chosenUser: User) {
    const apiEndpoint = 'http://localhost:8080/admin-service/user/block';
    this.http.post(apiEndpoint, chosenUser, {responseType: 'text', withCredentials: true}).subscribe(response => {
      this.ngOnInit();
    }, err => {
      console.log('Unable to deactive user: ', chosenUser);
    });
  }

  onApprove(chosenComment: Comment) {
    const apiEndpoint = 'http://localhost:8080/review-service/comment/approve';
    this.http.post(apiEndpoint, chosenComment, {responseType: 'text', withCredentials: true}).subscribe(() => {
      this.ngOnInit();
    }, err => {
      console.log('Could not approve the comment');
    });
  }

  onDecline(chosenComment: Comment) {
    const apiEndpoint = 'http://localhost:8080/review-service/comment/decline';
    this.http.post(apiEndpoint, chosenComment, {responseType: 'text', withCredentials: true}).subscribe(() => {
      this.ngOnInit();
    }, err => {
      console.log('Could not decline the comment');
    });
  }

}

export interface User {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  address: string;
  isActive: boolean;
  isBlocked: boolean;
  numberOfAdvertsCancelled: number;
};

export interface Comment {
  uuid: string;
  text: string;
  mark: number;
  username: string;
};