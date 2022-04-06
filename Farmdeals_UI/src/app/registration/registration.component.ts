import { Component, OnInit } from '@angular/core';
import { Inject }  from '@angular/core';
import { DOCUMENT } from '@angular/common'; 
import { LoginService } from '../login.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { farmerModel } from '../farmermodel';
import { dealerModel } from '../dealermodel';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
 login=false;
 register=true;
 formval=true
 userregister!: FormGroup;
farmerdata=new farmerModel('','','',0);
dealerdata=new dealerModel('','','',0);

  constructor(private fb:FormBuilder,private router :Router,private loginservice:LoginService,private snackBar: MatSnackBar) { }
  
  ngOnInit(): void {
    this.userregister= this.fb.group({
      username:['',[Validators.required,Validators.minLength(4)]],
      email:['',Validators.required],
      phnnum:['',[Validators.required,Validators.minLength(10)]],
      password:['',Validators.required],
      confirmpassword:['',Validators.required]
    },
    {
      validators:this.MustMatch('password','confirmpassword')
    }
    )
  }

  MustMatch(controlname:string,matchcontrolname:string){
    return(formGroup:FormGroup)=>{
      const control=formGroup.controls[controlname];
      const matchcontrol=formGroup.controls[matchcontrolname];
      if(matchcontrol.errors && !matchcontrol.errors['MustMatch']){
        return 
      }
      if(control.value!== matchcontrol.value){
        matchcontrol.setErrors({MustMatch:true})
      }else{
        matchcontrol.setErrors(null)
      }
    }
  }

  onSelect(name: any){
    if(name==1 && !this.loginservice.isLoggedIn()){
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

  farmerregister(){
    this.formval=true;
   this.snackBar.open('farmer role selected','ok', {
    duration: 3000,
    panelClass: 'custom-snackbar'
  });
  }
  dealerregister(){
    this.formval=false;
    this.snackBar.open('Dealer role selected','ok', {
      duration: 3000,
      panelClass: 'custom-snackbar'
    });
  }
loginclick(){
this.register=false
}

registerclick(){
  this.register=true
  }

  sendfarmerdata(farmer:any){
    if(this.userregister.valid){
      this.farmerdata.farmername=farmer.username;
      this.farmerdata.femail=farmer.email;
      this.farmerdata.fphn=farmer.phnnum;
      this.farmerdata.fpasswd=farmer.password
    this.loginservice.registerfarmer(this.farmerdata).subscribe();
    console.log(this.farmerdata)
    this.router.navigate(['/login',"farmer"]);
    }else{
      this.snackBar.open('Please fill Required Fields','ok', {
        duration: 3000,
        panelClass: 'red-snackbar'
      });
    }
  }

  senddealerdata(dealer:any){
    if(this.userregister.valid){
      this.dealerdata=new dealerModel(dealer.username,dealer.email,dealer.password,dealer.phnnum)
      this.loginservice.registerdealer(this.dealerdata).subscribe();
    this.router.navigate(['/login',"dealer"]);
    }else{
      this.snackBar.open('Please fill Required Fields','ok', {
        duration: 3000,
        panelClass: 'red-snackbar'
      });
    }
   
  }
  
}
