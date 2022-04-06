import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public loggedIn =false;
  public dloggedIn =false;
  public role=false;
  public userdata =[] as any
  constructor(private login:LoginService,private route:Router) { }

  ngOnInit(): void {
    
     if(localStorage.getItem("role")=="farmer"){
      this.login.getuserdata().subscribe(data=>this.userdata=data)
      this.loggedIn=this.login.isLoggedIn()
       this.role=true;
     }else if(localStorage.getItem("role")=="dealer"){
      this.login.getdealerdata().subscribe(data=>this.userdata=data)
       this.dloggedIn=true
       this.role=false;
     }else if(localStorage.getItem("role")=="admin"){
      this.login.getdealerdata().subscribe(data=>this.userdata=data)
      console.log(this.userdata)
       this.dloggedIn=true
       this.role=false;
     }
   
  }

  logoutUser(){
    this.login.logOut()
    location.reload()
    //this.route.navigate(['/home'])
  }

}
