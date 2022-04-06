import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { LoginComponent } from './login/login.component';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import { FormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { FarmerdashboardComponent } from './farmerdashboard/farmerdashboard.component';
import { LoginService } from './login.service';
import { ProfileComponent } from './profile/profile.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import { RegistrationComponent } from './registration/registration.component';
import {MatIconModule} from '@angular/material/icon';
import {ScrollingModule} from '@angular/cdk/scrolling';
import { CropsdataComponent } from './cropsdata/cropsdata.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { DealerdashboardComponent } from './dealerdashboard/dealerdashboard.component';
import { AddcropComponent } from './addcrop/addcrop.component';
import {MatDialogModule} from '@angular/material/dialog';
import { DialogOverviewExampleDialogComponent } from './dialog-overview-example-dialog/dialog-overview-example-dialog.component';
import { AdminService } from './admin.service';
import { AdminComponent } from './admin/admin.component';
import { AdmindashboardComponent } from './admindashboard/admindashboard.component';
import { ReactiveFormsModule } from '@angular/forms';
import{ ConfirmationPopoverModule} from 'angular-confirmation-popover';
import { TestloginComponent } from './testlogin/testlogin.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    FarmerdashboardComponent,
    ProfileComponent,
    RegistrationComponent,
    CropsdataComponent,
    DealerdashboardComponent,
    AddcropComponent,
    DialogOverviewExampleDialogComponent,
    AdminComponent,
    AdmindashboardComponent,
    TestloginComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatInputModule,
    FormsModule,
    HttpClientModule,
    MatCardModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatSelectModule,
    MatIconModule,
    ScrollingModule,
    MatSnackBarModule,
    MatDialogModule,
    ReactiveFormsModule,
    ConfirmationPopoverModule.forRoot({
      confirmButtonType:'danger'//set default value here
    })
  ],
  providers: [LoginService,AdminService],
  bootstrap: [AppComponent]
})
export class AppModule { }
