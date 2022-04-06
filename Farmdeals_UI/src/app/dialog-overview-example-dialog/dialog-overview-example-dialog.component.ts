import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AdminService } from '../admin.service';
import { DialogData } from '../cropsdata/cropsdata.component';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-dialog-overview-example-dialog',
  templateUrl: './dialog-overview-example-dialog.component.html',
  styleUrls: ['./dialog-overview-example-dialog.component.css']
})
export class DialogOverviewExampleDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,private route:Router,private admin:AdminService
  ) {}

  dloggedin=false
  ngOnInit(): void {
    if(localStorage.getItem("role")=="dealer"){
      this.dloggedin=true
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  directto(){
    this.route.navigate(['/login',"dealer"])
    this.dialogRef.close();
  }

  clearitem(){
    localStorage.removeItem('submitpay')
    this.dialogRef.close();
  }
  Click(){
    
  }
}
