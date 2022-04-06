import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  
  credentials={
    useremail:'',
    password:''
  }
  public showPassword: boolean = false;
  public pageid=[] as any;
  public errormsg=[] as any;
  public val=[] as any;
  constructor(private login : LoginService,private route :ActivatedRoute) { }

  ngOnInit(): void {
    this.pageid = this.route.snapshot.params['name'];
  }

  onselect(){
    this.credentials.useremail='';
    this.credentials.password='';
  }
  public togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }
  onSubmit(){
    if(this.pageid=='farmer'){
      if((this.credentials.useremail!=''&& this.credentials.password!='')&&(this.credentials.useremail!=null&& this.credentials.password!=null)){
        this.login.generateToken(this.credentials).subscribe(
          (response:any)=>{
            console.log(response.response);
            this.val=response.response;
            this.login.loginUser(response.response,"farmer");
            if(this.val!="Not an Authorized user"){
              window.location.href="/farmerdashboard"
              
            }else{
              this.errormsg="Please enter valid Credentials"
              this.credentials.useremail='';
              this.credentials.password='';
            }
          },
        )
      }else{
        console.log("Fields are empty")
      }
    }
    else if(this.pageid=='dealer'){

      if((this.credentials.useremail!=''&& this.credentials.password!='')&&(this.credentials.useremail!=null&& this.credentials.password!=null)){
        this.login.generateTokenfordealer(this.credentials).subscribe(
          (response:any)=>{
            console.log(response.response);
            this.val=response.response;
            this.login.loginUser(response.response,"dealer")
            
            if(this.val!="Not an Authorized user"){
              window.location.href="/dealerdashboard"
              
            }else{
              this.errormsg="Please enter valid Credentials"
              this.credentials.useremail='';
              this.credentials.password='';
            }
          }, 
        )
        
      }else{
        console.log("Fields are empty")
      }
    }  
  }
}
