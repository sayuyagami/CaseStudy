import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from '../admin.service';
import { DialogOverviewExampleDialogComponent } from '../dialog-overview-example-dialog/dialog-overview-example-dialog.component';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-admindashboard',
  templateUrl: './admindashboard.component.html',
  styleUrls: ['./admindashboard.component.css']
})
export class AdmindashboardComponent implements OnInit {
  farmerslist=[] as any;
  dealerslist=[] as any;
  public popoverTitle:string="Record Delete Confirmation"
  public popoverMessage:string="Are you sure to delete?"
  public confirmClicked:boolean=false
  public cancelClicked:boolean=false

  farmerdata={
    farmerid:0,
    femail:'',
    farmername:'',
    fphn:0,
  }
  dealerdata={
    dealerid:0,
    demail:'',
    dealername:'',
    dphn:0,
  }
  result:any
  msg: any;
  resp=[] as any
  constructor(private adminservice:AdminService,private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.adminservice.getfarmerslist().subscribe(
      data=>{this.farmerslist=data;
        console.log([...this.farmerslist])
   
  });
  this.adminservice.getdealerslist().subscribe(
    data=>{this.dealerslist=data;
      console.log([...this.dealerslist])});
  }

  farmeredit(f:any){
    this.farmerdata.farmerid=f.farmerid
    this.farmerdata.femail=f.femail;
    this.farmerdata.farmername=f.farmername;
    this.farmerdata.fphn=f.fphn;
  }

  dealeredit(d:any){
    this.dealerdata.dealerid=d.dealerid
    this.dealerdata.demail=d.demail;
    this.dealerdata.dealername=d.dealername;
    this.dealerdata.dphn=d.dphn;
  }

  updatefarmer(){
    this.adminservice.updatefarmeradmin(this.farmerdata,this.farmerdata.farmerid).subscribe()
    
    // this.snackBar.open('Updated Farmer Record','ok', {
    //   duration: 3000,
    //   panelClass: 'custom-snackbar'
    // });
    location.reload()
  }

  updatedealer(){
    this.adminservice.updatedealeradmin(this.dealerdata,this.dealerdata.dealerid).subscribe()
    
    // this.snackBar.open('Updated Dealer Record','ok', {
    //   duration: 3000,
    //   panelClass: 'custom-snackbar'
    // });
    location.reload()
  }

  farmerdelete(fid:number){
    this.adminservice.deletefarmer(fid).subscribe()
    
    // this.snackBar.open('Deleted Farmer Record','ok', {
    //   duration: 3000,
    //   panelClass: 'red-snackbar'
    // });
    location.reload()
  }

  dealerdelete(did:number){
    this.adminservice.deletedealer(did).subscribe()
    
    // this.snackBar.open('Deleted Dealer Record','ok', {
    //   duration: 3000,
    //   panelClass: 'red-snackbar'
    // });
    location.reload()
  }

 
}
