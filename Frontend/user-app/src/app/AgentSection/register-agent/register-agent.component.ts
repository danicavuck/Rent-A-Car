import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-agent',
  templateUrl: './register-agent.component.html',
  styleUrls: ['./register-agent.component.css']
})
export class RegisterAgentComponent implements OnInit {

  model: Agent = {
    agentName: '',
    password: '',
    address: '',
    email: '',
    registrationNumber: ''
  };

  password2: '';

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

  async onSubmit() {
    const apiEndpoint = 'http://localhost:8080/auth-service/register/agent';
    
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
    return this.model.agentName.length > 0 && this.model.address.length > 0 
    && this.model.email.length > 0 && this.model.registrationNumber.length > 0;
  }

}

interface Agent {
  agentName: string;
  password: string;
  address: string;
  email: string;
  registrationNumber: string;
};
