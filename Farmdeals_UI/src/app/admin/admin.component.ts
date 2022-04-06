import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  credentials={
    useremail:'',
    password:''
  }
  val: any;
  errormsg: string | undefined;
  
  constructor(private login:LoginService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    if((this.credentials.useremail!=''&& this.credentials.password!='')&&(this.credentials.useremail!=null&& this.credentials.password!=null)){
      this.login.generateTokenfordealer(this.credentials).subscribe(
        (response:any)=>{
          console.log(response.response);
          this.val=response.response;
          if(this.credentials.useremail=="farmdeals37@gmail.com"){
            this.login.loginUser(response.response,"admin");
          }
          
          if(this.val!="Not an Authorized user"  ){
            window.location.href="/admindashboard"
            
          }else{
            this.errormsg="Please enter valid Credentials"
            this.credentials.useremail='';
            this.credentials.password='';
          }
        },
      );
    }else{
      console.log("Fields are empty")
    }
  
  }
}
