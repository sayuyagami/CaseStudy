import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url='http://localhost:8100'
  
  farmerdetaildata={
    token:localStorage.getItem("token"),
    passedUrl:'farmerprofile'
  }

  dealerdetaildata={
    token:localStorage.getItem("token"),
    passedUrl:'dealersprofile'
  }

  dealerdetailsubdata={
    token:localStorage.getItem("token"),
    passedUrl:'subscribe'
  }
  dealerdetailunsubdata={
    token:localStorage.getItem("token"),
    passedUrl:'unsubscribe'
  }

  postsdata={
    token:localStorage.getItem("token"),
    passedUrl:'LoggedInuserposts'
  }
 
  constructor(private http:HttpClient) { }

  generateToken(credentials: any){
    
    return this.http.post(`${this.url}/farmerauth`,credentials)
  }

  generateTokenfordealer(credentials: any){
    return this.http.post(`${this.url}/dealerauth`,credentials)
  }

  registerfarmer(farmerregdata:any){
    return this.http.post(`${this.url}/agrofarmer/farmerregister`,farmerregdata)
  }

  registerdealer(dealerregdata:any){
    return this.http.post(`${this.url}/agrodealer/dealerregister`,dealerregdata)
  }

  //setting generated token
  loginUser(token: string,roleid :string){ 
    localStorage.setItem("role",roleid)
    localStorage.setItem("token",token)
    return true;
  }

  //check token
  isLoggedIn(){
    let token=localStorage.getItem("token")
    if(token==undefined|| token==='' || token==null || token=='Not an Authorized user'){
      return false
    }else{
      return true
    }
  }

  getuserdata(){
    return this.http.post(`${this.url}/agrofarmers`,this.farmerdetaildata)
  }

  getImagelist() {
    return this.http.get(`${this.url}/agroadmin/admin/getimglist`).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
      
    }))
  }

 

  updatefarmerdata(userdetails:any){
    return this.http.post(`${this.url}/agroupdate/${this.farmerdetaildata.token}/editdet`,userdetails)
  }

  updatedealerdata(userdetails:any){
    return this.http.post(`${this.url}/agroupdate/${this.dealerdetaildata.token}/dealereditdet`,userdetails)
  }

  getuserposts(){
    return this.http.post(`${this.url}/agrofarmers`,this.postsdata).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
      
    }))
  }

  adduserpost(newpost:any,croptype:any){
    return this.http.post(`${this.url}/agroadmin/admin/addcrop/${croptype}`,newpost)
  } 

  edituserpost(editpost:any,userid:any){
    return this.http.put(`${this.url}/agroadmin/admin/editcrop/${userid}`,editpost)
  }

  getsubscribeddealerdata(dealerid:any){
    return this.http.get(`${this.url}/agrodealer/dealers/admin/${dealerid}`)
  }
  getsubscribedfarmerdata(fid:any){
    return this.http.get(`${this.url}/agrofarmer/farmers/id/${fid}`)
  }

  allcropsdata(){
    return this.http.get(`${this.url}/agroadmin/admin/cropslist`).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
    }))
  }
  /**dealer apis */
  getdealerdata(){
    return this.http.post(`${this.url}/agrodealers`,this.dealerdetaildata)
  }

  getdealerdatabyid(dealerid:any){
    return this.http.post(`${this.url}/agrodealers/admin/${dealerid}`,this.dealerdetaildata)
  }

  dealersubscribe(fid:any){
    return this.http.post(`${this.url}/agrodealersubs/${fid}`,this.dealerdetailsubdata)
  }

  dealerunsubscribe(fid:any){
    return this.http.post(`${this.url}/agrodealersubs/${fid}`,this.dealerdetailunsubdata)
  }

  getsubscribedposts(dealerid:any){
    return this.http.get(`${this.url}/agroadmin/admin/dealersubscribedlist/${dealerid}`).pipe(map(
      (res:any)=>{
        if(res!=null){
          return res;  
        }else{
          res="no data found"
          return res;
        }
    }))
  }

  getkharifcrops(){
    return this.http.get(`${this.url}/agroadmin/admin/Kharifcropslist`).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
    }))
  }

  getrabbicrops(){
    return this.http.get(`${this.url}/agroadmin/admin/Rabbicropslist`).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
    }))
  }

  getcashcrops(){
    return this.http.get(`${this.url}/agroadmin/admin/Cashcropslist`).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
    }))
  }

  dealerreceipts(dealerid:number){
  return this.http.get(`${this.url}/agroadmin/admin/receiptsbydealerid/${dealerid}`).pipe(map((res:any)=>{
    if(res!=null){
      return res;  
    }else{
      res="no data found"
      return res;
    }
  }))
  }

  farmerreceipts(farmerid:number){
    return this.http.get(`${this.url}/agroadmin/admin/receiptsbyfarmerid/${farmerid}`).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
    }))
    }

  payment(paydetails:any){
    return this.http.post(`${this.url}/agroadmin/admin/paydata`,paydetails)
  }
  //logout the user
  logOut(){
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    return true
  }

}
