import { Component, OnChanges, SimpleChanges, Input } from '@angular/core';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnChanges {

  roles: Role[] = [
      {value: "USER", viewValue: "User"},
      {value: "AGENT", viewValue: "Agent"},
      {value: "ADMIN", viewValue: "Admin"}
  ];

  role: Role = {
    value: '',
    viewValue: ''
  };

  adminPreview: boolean = false;
  userPreview: boolean = false;
  agentPreview: boolean = false;

  
  constructor() {
  }

  selected: '';
  @Input() number: number = 1;
  ngOnChanges(changes: SimpleChanges) {
    console.log("changes");
  }

  async clicked() {
    this.number += 1;
    console.log(this.number);
  }

  public getSelected() {
    return this.selected;
  }

  public setSelected(value) {
    this.selected = value;
    switch(value){
      case "AGENT" : this.agentPreview = true;
                     this.adminPreview = false;
                     this.userPreview = false;
                     break;
      case "ADMIN" : this.adminPreview = true;
                     this.agentPreview = false;
                     this.userPreview = false;
                     break;
      case "USER" :  this.userPreview = true;
                     this.agentPreview = false;
                     this.adminPreview = false;
                     break;
    }
  }

}

interface Role {
  value: string;
  viewValue: string;
};