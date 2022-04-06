import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DialogOverviewExampleDialogComponent } from '../dialog-overview-example-dialog/dialog-overview-example-dialog.component';
import { LoginService } from '../login.service';


export interface DialogData {
  msg:string
}

@Component({
  selector: 'app-cropsdata',
  templateUrl: './cropsdata.component.html',
  styleUrls: ['./cropsdata.component.css']
})
export class CropsdataComponent implements OnInit {

  cropname : String ="";
  newcropdata={
  croplocation: '',
  cropname: '',
  cropprice: 0,
  cropqty: '',
  croptype:'',
  farmerid:'',
  farmername:''
  };

  retrievedImage: any;

  panelOpenState = false;
  public crops=[] as any
  public cropslist=[] as any
  public reqlist=[] as any
  lengthvalue =false
  base64Data: any;
  retrieveResonse: any;
  message!: string;
  cropidlst=[] as any
  dloggedIn=false
  userdata=[] as any
  msg:any;
  subs=[] as any

  constructor(public dialog: MatDialog,private login:LoginService,private snackBar: MatSnackBar) { }

  ngOnInit() {
   
    if(localStorage.getItem("role")=="dealer"){
     
      this.dloggedIn=true
      this.login.getdealerdata().subscribe(data=>{this.userdata=data;
        if(this.userdata.subs!=null){
          this.callmethod(this.userdata.subs)//fid
        }else{
          this.displaycrops()
        }       
       })
       
    }else{
      this.msg="Please loggin as dealer"
      this.displaycrops()
  }
  }

  displaycrops(){
    this.login.allcropsdata().subscribe(

      data=>{this.cropslist=[...data];
        this.crops=this.cropslist.reverse()
        console.log(this.crops)
        console.log(this.crops)
        for(var item in this.crops){
          this.base64Data=data[item].picByte;
          data[item].image='data:image/jpeg;base64,' + this.base64Data;
          data[item].requests=null
      }
    }
    );
  }

  kharif(){
    this.login.getkharifcrops().subscribe(
      data=>{this.crops=[...data];
        console.log(this.crops)
        for(var item in this.crops){
          this.base64Data=data[item].picByte;
          data[item].image='data:image/jpeg;base64,' + this.base64Data;
          data[item].requests=null
      }
    }
    );
  }
  rabbi(){
   this.login.getrabbicrops().subscribe(
      data=>{this.crops=[...data];
        console.log(this.crops)
        for(var item in this.crops){
          this.base64Data=data[item].picByte;
          data[item].image='data:image/jpeg;base64,' + this.base64Data;
          data[item].requests=null
      }
    }
    );
  }

  cash(){
    this.login.getcashcrops().subscribe(
      data=>{this.crops=[...data];
        console.log(this.crops)
        for(var item in this.crops){
          this.base64Data=data[item].picByte;
          data[item].image='data:image/jpeg;base64,' + this.base64Data;
          data[item].requests=null
      }
    }
    );
  }

  subscribe(boolval:boolean,fid:number){
    if(boolval==null){
      this.login.dealersubscribe(fid).subscribe();
      this.snackBar.open('Subscribed successfully','close', {
        duration: 3000
      });
      location.reload()
    }else if(boolval==true){
      this.login.dealerunsubscribe(fid).subscribe();
      this.snackBar.open('UnSubscribed successfully','close', {
        duration: 3000
      });
      location.reload()
    }
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(DialogOverviewExampleDialogComponent, {
      width: '250px',
      data: {msg: this.msg},
    });

    dialogRef.afterClosed().subscribe();
      
  }

  callmethod(farmidlst:any){
    this.login.allcropsdata().subscribe(
      data=>{
        console.log(farmidlst)
      
          this.cropslist=[...data];
          this.crops=this.cropslist.reverse()
        console.log(this.crops)
        
          
            for(var item in this.crops){
              this.base64Data=data[item].picByte;
              data[item].image='data:image/jpeg;base64,' + this.base64Data;
              for(var i in farmidlst){
              if(data[item].farmerid===farmidlst[i]){
                data[item].requests=true
              }
            }
          }      
      } 
    );

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

  Onsearch(){
    if(this.cropname==''){
      this.ngOnInit()
    }else{
  
      this.crops= this.crops.filter((res: { cropname: string; })=>{
        return res.cropname.toLocaleLowerCase().match(this.cropname.toLocaleLowerCase());
      })
  }
}

directto(c:any){
  if(this.dloggedIn==true){
     this.login.getdealerdata().subscribe()
      this.login.dealersubscribe(c.cropid).subscribe();
    }
}

}

