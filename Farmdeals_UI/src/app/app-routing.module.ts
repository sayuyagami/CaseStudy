import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddcropComponent } from './addcrop/addcrop.component';
import { AdminComponent } from './admin/admin.component';
import { AdmindashboardComponent } from './admindashboard/admindashboard.component';
import { AuthGuard } from './auth.guard';
import { CropsdataComponent } from './cropsdata/cropsdata.component';
import { DealerdashboardComponent } from './dealerdashboard/dealerdashboard.component';
import { FarmerdashboardComponent } from './farmerdashboard/farmerdashboard.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { RegistrationComponent } from './registration/registration.component';
import { RoleGuard } from './role.guard';
import { TestloginComponent } from './testlogin/testlogin.component';

const routes: Routes = [
  {path:'',redirectTo:'home',pathMatch:'full'},
  {path :'home',component : HomeComponent},
  {path :'test',component : TestloginComponent},
  {path :'cropslist',component : CropsdataComponent},
  {path :'registerpage',component : RegistrationComponent},
  {path :'profile',component : ProfileComponent,canActivate:[AuthGuard]},
  {path :'login/:name',component : LoginComponent},
  {path : 'admin',component:AdminComponent},
  {path : 'admindashboard',component:AdmindashboardComponent,canActivate:[AuthGuard,RoleGuard],data:{
    role:'admin'
  },
 },
  {path : 'farmerdashboard',component:FarmerdashboardComponent,canActivate:[AuthGuard,RoleGuard],
  data:{
    role:'farmer'
  },
},
  {path : 'farmerdashboard/addpost',component:AddcropComponent,canActivate:[AuthGuard]},
  {path : 'dealerdashboard',component:DealerdashboardComponent,canActivate:[AuthGuard,RoleGuard],
  data:{ role:'dealer'}}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
