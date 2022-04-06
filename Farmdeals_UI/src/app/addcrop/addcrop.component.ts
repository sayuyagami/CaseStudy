import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-addcrop',
  templateUrl: './addcrop.component.html',
  styleUrls: ['./addcrop.component.css']
})
export class AddcropComponent implements OnInit {

cropform!: FormGroup;
  selectedFile!: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message!: string;
  imageName: any;
  userdetails:any;
  newcropdata={
    croplocation: '',
    cropname: '',
    cropprice: 0,
    cropqty: '',
    croptype:'',
    farmerid:'',
    farmername:''
    };

  constructor(private login:LoginService,private fb:FormBuilder,private snackBar: MatSnackBar,private http:HttpClient) { }

  ngOnInit(): void {
    this.login.getuserdata().subscribe(data=>this.userdetails=data)
    this.cropform=this.fb.group({
     
      cropname:['',Validators.required],
      cropprice:['',Validators.required],
      cropqty:['',Validators.required],
      croptype:['',Validators.required],
      croplocation:['',Validators.required],
    }
    )
  }

  addpost(crop:any){
   if(this.cropform.valid){
     this.newcropdata.cropname=crop.cropname;
     this.newcropdata.cropqty=`${crop.cropqty}kgs`
     this.newcropdata.cropprice=crop.cropprice
     this.newcropdata.croptype=crop.croptype
     this.newcropdata.croplocation=crop.croplocation
    this.newcropdata.farmerid=this.userdetails.farmerid
    this.newcropdata.farmername=this.userdetails.farmername
    this.login.adduserpost(this.newcropdata,this.newcropdata.croptype).subscribe()
    location.reload()
    this.snackBar.open('Added Crop Successfully!!','ok', {
      duration: 3000,
      panelClass: 'green-snackbar'
    });
   }else{
    this.snackBar.open('Please fill Required Fields','ok', {
      duration: 3000,
      panelClass: 'red-snackbar'
    });
  }
    
  }

   //Gets called when the user selects an image
   public onFileChanged(event: any) {
    //Select File
    this.selectedFile = event.target.files[0];
  }


  //Gets called when the user clicks on submit to upload the image
  onUpload() {
    console.log(this.selectedFile);
    
    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
  
    //Make a call to the Spring Boot Application to save the image
    this.http.post('http://localhost:8081/admin/upload', uploadImageData, { observe: 'response' })
      .subscribe((response: { status: number; }) => {
        if (response.status === 200) {
          this.message = 'Image uploaded successfully';
        } else {
          this.message = 'Image not uploaded successfully';
        }
      }
      );
      this.snackBar.open('Image uploaded successfully','ok', {
        duration: 3000,
        panelClass: 'green-snackbar'
      });

  }

    //Gets called when the user clicks on retieve image button to get the image from back end
   
}
