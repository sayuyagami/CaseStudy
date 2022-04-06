import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  farmerdetails={
    farmername:'',
    fphn:0
  }

  dealerdetails={
    dealername:'',
    dphn:0
  }

  public userdata =[] as any
  rolecheck=false  
  resp=[] as any
  receipts=[] as any
  noreceipts=true
  
  constructor(private login:LoginService) { }

  ngOnInit(): void {
    if(localStorage.getItem("role")=="farmer"){
      this.rolecheck=true
      this.login.getuserdata().subscribe(data=>{this.userdata=data;
      this.farmerdetails.farmername=this.userdata.farmername
      this.farmerdetails.fphn=this.userdata.fphn
      this.getfarmerreceipts(this.userdata.farmerid)  
    })
      
    }else if(localStorage.getItem("role")=="dealer"){
      this.rolecheck=false
      this.login.getdealerdata().subscribe(data=>{this.userdata=data;
      this.dealerdetails.dealername=this.userdata.dealername
      this.dealerdetails.dphn=this.userdata.dphn
      console.log(this.userdata.dealerid)
      this.getdealerreceipts(this.userdata.dealerid)
      })     
    }
  }

  getfarmerreceipts(famerid:number){
    this.login.farmerreceipts(famerid).subscribe(
      data=>
        this.receipts=data
    )
  }
  getdealerreceipts(dealid:number){
    this.login.dealerreceipts(dealid).subscribe(
      data=>{if(data!="no data found"){
        this.noreceipts=false
        this.receipts=data
      }
    }
    )
  }
  updatefarmer(){
    this.login.updatefarmerdata(this.farmerdetails).subscribe(data=>this.resp=data)
    console.log(this.resp)
    location.reload()
  }
  
  updatedealer(){
    this.login.updatedealerdata(this.dealerdetails).subscribe(data=>this.resp=data)
    console.log(this.resp)
    location.reload()
  }
}
