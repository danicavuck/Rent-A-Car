import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { RouterModule, Routes} from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';

// Components
import { LoginComponent } from './login/login/login.component';
import { RegistrationComponent } from './registration/registration/registration.component';
import { PageNotFoundComponent } from './PageNotFound/page-not-found/page-not-found.component';
import { AdminRegistrationComponent } from './registration/modules/admin-registration/admin-registration.component';
import { UserRegistrationComponent } from './registration/modules/user-registration/user-registration.component';
import { AgentRegistrationComponent } from './registration/modules/agent-registration/agent-registration.component';
import { UserHomepageComponent } from './UserSection/user-homepage/user-homepage.component';
import { AdminHomepageComponent } from './AdminSection/admin-homepage/admin-homepage.component';
import { AgentHomepageComponent } from './AgentSection/agent-homepage/agent-homepage.component';
import { from } from 'rxjs';


const appRouts: Routes = [
  { path: 'login', component : LoginComponent },
  { path: 'registration', component : RegistrationComponent },
  { path: 'user', component: UserHomepageComponent },
  { path: 'admin', component: AdminHomepageComponent },
  { path: 'agent', component: AgentHomepageComponent },
  { path: '', redirectTo : '/login', pathMatch : 'full' },
  { path: '**', component : PageNotFoundComponent }
];



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    PageNotFoundComponent,
    AdminRegistrationComponent,
    UserRegistrationComponent,
    AgentRegistrationComponent,
    UserHomepageComponent,
    AdminHomepageComponent,
    AgentHomepageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    MatTooltipModule,
    RouterModule.forRoot(appRouts)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
