import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: LoginViewModel = {
    username: '',
    password: ''
  };

  userDTO: UserDTO = {
    id: 0,
    user_email: '',
    role: ''
  };

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

  async onSubmit() {

    if(this.performCheck()) {
      const apiEndpoint = 'http://localhost:8080/auth-service/login';

      this.http.post(apiEndpoint, this.model, {
        responseType: 'text', withCredentials: true
      }).subscribe(data => {
        localStorage.setItem('username', this.model.username);
        localStorage.setItem('loggedIn', 'true');
          switch(data) {
            case 'ADMIN': this.router.navigateByUrl('/admin');
                          break;
            case 'USER':  this.router.navigateByUrl('/home');
                          break;
            case 'AGENT': this.router.navigateByUrl('/agent');
                          break;
            default: console.log('Invalid response');       
          }
      }, err => {
        console.log('Error: ' + err);
      });
    }
  }

  async performCheck() {
    if(this.model.username === '' || this.model.password === '') {
      alert('Fields cannot be empty');
      return false;
    }
    return true;
  }

}

export interface LoginViewModel {
  username: string;
  password: string;
}

export interface UserDTO {
  id: number;
  user_email: string;
  role: string;
}