import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  model: User = {
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    address: '',
    email: '',
  };
  password2: '';

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

  async onSubmit() {
    const apiEndpoint = 'http://localhost:8080/auth-service/register/user';
    
    if(this.validatePasswords()) {
      if(this.isFormValid()) {
        this.http.post(apiEndpoint, this.model, {responseType: 'text', withCredentials: true}).subscribe(data => {
          this.router.navigateByUrl('/login');
        }, err => {
          console.log(err);
        });
      } else {
        alert('Form is invalid');
      }
    } else {
      alert('Passwords must match!');
    }
  }

  validatePasswords() {
    return this.password2 === this.model.password && this.password2.length > 0;
  }

  isFormValid() {
    return this.model.username.length > 0 && this.model.firstName.length > 0 
    && this.model.lastName.length > 0 && this.model.address.length > 0 && this.model.email.length > 0;
  }

}

interface User {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  address: string;
  email: string;
};
