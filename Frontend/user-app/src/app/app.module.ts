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
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';

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
import { HomepageComponent } from './homepage/homepage.component';
import { AddAdvertComponent } from './UserSection/add-advert/add-advert.component';
import { ReviewComponent } from './review/review.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AdvertComponent } from './advert/advert.component';
import { ProfileComponent } from './UserSection/profile/profile.component';
import { MessageComponent } from './message/message.component';
import { InboxComponent } from './inbox/inbox.component';
import { InboxContactsComponent } from './inbox-contacts/inbox-contacts.component';
import { InboxMessangerComponent } from './inbox-messanger/inbox-messanger.component';
import { ManagingEntitiesComponent } from './AdminSection/managing-entities/managing-entities.component';


const appRouts: Routes = [
  { path: 'login', component : LoginComponent },
  { path: 'registration', component : RegistrationComponent },
  { path: 'user', component: UserHomepageComponent },
  { path: 'user/profile', component: ProfileComponent },
  { path: 'inbox', component: InboxComponent },
  { path: 'admin', component: AdminHomepageComponent },
  { path: 'admin/managing', component: ManagingEntitiesComponent },
  { path: 'agent', component: AgentHomepageComponent },
  { path: 'advert', component: AddAdvertComponent },
  { path: 'details', component: AdvertComponent },
  { path: 'home', component: HomepageComponent },
  { path: '', redirectTo : '/home', pathMatch : 'full' },
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
    AgentHomepageComponent,
    HomepageComponent,
    AddAdvertComponent,
    ReviewComponent,
    AdvertComponent,
    ProfileComponent,
    MessageComponent,
    InboxComponent,
    InboxContactsComponent,
    InboxMessangerComponent,
    ManagingEntitiesComponent
  ],
  entryComponents: [
    ReviewComponent,
    MessageComponent
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
    MatCheckboxModule,
    MatDialogModule,
    RouterModule.forRoot(appRouts),
    NgbModule
  ],
  providers: [],
  exports: [
    InboxComponent,
    InboxContactsComponent,
    InboxMessangerComponent
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
