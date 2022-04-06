import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RouteConfigLoadEnd, Router } from '@angular/router';
import { DialogOverviewExampleDialogComponent } from '../dialog-overview-example-dialog/dialog-overview-example-dialog.component';
import { LoginService } from '../login.service';
import { payModel } from '../Paymodel';

@Component({
  selector: 'app-dealerdashboard',
  templateUrl: './dealerdashboard.component.html',
  styleUrls: ['./dealerdashboard.component.css']
})
export class DealerdashboardComponent implements OnInit {

  
  cropname : String ="";
  
  panelOpenState = false;
  public crops=[] as any
  public reqlist=[] as any
  lengthvalue =false
  userdata=[] as any
  public cropdata=[] as any;
  base64Data: any;
  retrieveResonse: any;
  message!: string;
  cropidlst=[] as any
  retrievedImage: any;
  public nodata = false;
  arr: any;
  slst= [] as any
  nosubscribes= false;
  subfarmers=[]as any
  msg:string=''
  submitpay=false;

  constructor(private login:LoginService,public dialog: MatDialog,private route:Router) { }

  ngOnInit() {
   this.login.getdealerdata().subscribe(data=>
    {this.userdata=data;
      if(this.userdata.subs!=null){
        this.nodata=false
        this.callsubscribedview(this.userdata.subs)
       this.callmethod(this.userdata.dealerid)
      }else{
        this.nodata=true
        this.nosubscribes=true
      }
  })
  
    this.invokeStripe()
 
  }

  onSelectpay(crpid:any){
   
    this.login.getsubscribedposts(this.userdata.dealerid).subscribe(data=>{
      console.log(data)
      if(data!="no data found"){
        this.nodata=false
        this.crops=[...data];
     
        for(var item in this.crops){
          if(data[item].cropid==crpid){
            data[item].cropqty="1"
            this.cropdata=data[item]
          }
        }
      }else{
        this.nodata=true
      }
      
    }
    );
 
  }

  pagereload(){
    location.reload()
  }

  callsubscribedview(userobj:any){
  
    for(var i in userobj){
      console.log(userobj[i])
      if(userobj[i]!=null){
        this.nosubscribes=false
        this.login.getsubscribedfarmerdata(userobj[i]).subscribe(
          data=>{this.subfarmers.push(data);        
          }
        )  
      }else{
        this.nosubscribes=true
      }
      
    }
      
  }
 
  

  callmethod(dealid:number){
     this.login.getsubscribedposts(dealid).subscribe(data=>{this.crops=[...data];
     
         if(data!="no data found"){
          // this.nodata=true
           for(var item in this.crops){
          
             this.base64Data=data[item].picByte;
             data[item].image='data:image/jpeg;base64,' + this.base64Data;
        }
        }
        
     }
   )

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

paymentcall(paydata:any){
  var p =new payModel(this.userdata.dealerid,this.userdata.dealername,paydata.farmerid,paydata.farmername,paydata.cropqty,paydata.cropqty*paydata.cropprice,paydata.cropid,paydata.cropname)
  console.log(p)
  this.msg=`Hi ${this.userdata.dealername} your payment for ${paydata.cropname} ${paydata.cropqty*paydata.cropprice} is done successfully!!`
  this.login.payment(p).subscribe()
this.makepayment(paydata.cropqty*paydata.cropprice)
}
makepayment(amt:number){
  console.log(amt)
  const paymentHandler=(<any>window).StripeCheckout.configure({
    key:
    'pk_test_51KevBaSIWr8fKn2Y3A5MbG0LF5jeMX8J8n8GfR2Xyk2ImBH3T08qQ4TCvUkZxL0V1iBcPS9HeCu5Mp14sz3MLi9w00BDTXsd4h',
    locale:'auto',
    token:function(stripeToken:any){
      console.log(stripeToken.card.name);
      if(stripeToken.card.name!=null){
        
          localStorage.setItem('submitpay','true')
          location.reload()
        
      }
    },
  },
  
  );
  
  paymentHandler.open({
    name:'FarmDeals',
    description:'Your crop is added to cart',
    amount:amt*100,
  },
   
  )
  
}

invokeStripe() {
  if(this.submitpay==false){
  if (!window.document.getElementById('stripe-script')) {
  const script = window.document.createElement('script');
  script.id = 'stripe-script';
  script.type = 'text/javascript';
  script.src = "https://checkout.stripe.com/checkout.js";
  window.document.body.appendChild(script);
  if(localStorage.getItem('submitpay')=='true'){
    this.openDialog()
  }
  }
}
}
openDialog(): void {
  const dialogRef = this.dialog.open(DialogOverviewExampleDialogComponent, {
    width: '250px',
    data: {msg: this.msg},
  });

  dialogRef.afterClosed().subscribe();
    
}
}

