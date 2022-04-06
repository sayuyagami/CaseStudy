import { HttpClient } from '@angular/common/http';
import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-farmerdashboard',
  templateUrl: './farmerdashboard.component.html',
  styleUrls: ['./farmerdashboard.component.css'],
  
})
export class FarmerdashboardComponent implements OnInit {
  
  

  cropname : String ="";
 

  updatecropdata={
    cropid:0,
    cropprice: 0
  }

  panelOpenState = false;
  public crops=[] as any
  lengthvalue =false
  reqlist: any;
  public cropdata=[] as any;
  userdetails:any;
  usersubs: any;
  slst:any;
  subscribeddealers:any
  public cropslist=[] as any
  base64Data: any;
  retrieveResonse: any;
  message!: string;
  cropidlst=[] as any
  retrievedImage: any;
  crpsdatawithimg=[] as any;
  public nodata = true;
  
  arr=[] as any;
  subdealers=[] as any;
  nosubscribes=false
  userdata=[] as any
  
  constructor(private login:LoginService,private route: Router) { }

  ngOnInit() {
    
    this.login.getuserposts().subscribe(

      data=>{
        if(data!="no data found"){
          console.log(data)
          this.nodata=false
          this.cropslist=[...data];
          this.crops=this.cropslist.reverse()
        for(var item in this.crops){
          this.base64Data=data[item].picByte;
          data[item].image='data:image/jpeg;base64,' + this.base64Data;
      }
    }
  }
    );
    this.login.getuserdata().subscribe(data=>
      {this.userdetails={...data};
        this.callsubscribedview(this.userdetails.subs)
      
      }
      )
    
  }

  callsubscribedview(userobj:any){
  
    for(var i in userobj){
      console.log(userobj[i])
      if(userobj[i]!=null){
        this.nosubscribes=false
        this.login.getsubscribeddealerdata(userobj[i]).subscribe(
          data=>{this.subdealers.push(data);  
            console.log(this.subdealers)      
          }
        )  
      }else{
        this.nosubscribes=true
      }
      
    }
      
  }

  Routeto(){
    this.route.navigate(['/farmerdashboard',"addpost"])
  }

  onselect(crop:any){
    if(crop.requests.length!=null){
      this.lengthvalue=true
      this.login.getuserposts().subscribe(
        data=>{this.crops = [...data] ;
          for(var item in this.crops){
            if(data[item].cropid==crop.cropid){
              this.reqlist=crop.requests
            }
          }
      }
      );
     
    }else{
      this.lengthvalue=false
    }
  }

  onSelectedit(crpid:any){
   
      this.login.getuserposts().subscribe(
        data=>{this.crops = [...data] ;
          for(var item in this.crops){
            if(data[item].cropid==crpid){
              this.cropdata=data[item]
            }
          }
      }
      );
   
  }

  editpost(){
    console.log(this.userdetails.farmerid)
    this.login.edituserpost(this.cropdata,this.userdetails.farmerid).subscribe()
    location.reload()
  }

  pagereload(){
    location.reload()
  }
  Onsearch(){
    if(this.cropname==''){
      this.ngOnInit()
    }else{
  
      this.crops= this.crops.filter((res: { cropname: string; })=>{
        return res.cropname.toLocaleLowerCase().match(this.cropname.toLocaleLowerCase());
      })
  }
}

}

