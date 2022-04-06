import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router :Router,private login:LoginService) { }

  ngOnInit(): void {
  }

  onSelect(name: any){
    if(name==1 && !this.login.isLoggedIn()){
      const nameval="farmer";
      this.router.navigate(['/login',nameval])
    }else if(name==2){
      const nameval="dealer";
      this.router.navigate(['/login',nameval])
    }else{
      if(localStorage.getItem("role")=="farmer"){
        this.router.navigate(['/farmerdashboard'])
      }else{
        this.router.navigate(['/dealerdashboard'])
      }
    }
    
  }
}
