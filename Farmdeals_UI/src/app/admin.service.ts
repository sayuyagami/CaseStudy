import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  url='http://localhost:8100'
  constructor(private http:HttpClient) { }

  getfarmerslist(){
    return this.http.get(`${this.url}/agrofarmer/farmers/admin/farmerslist`).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
    }));
  }

  getdealerslist(){
    return this.http.get(`${this.url}/agrodealer/dealers/admin/dealerslist`).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
    }));
  }

  updatefarmeradmin(userdetails:any,fid:number){
    return this.http.put(`${this.url}/agrofarmer/farmers/admin/editdet/${fid}`,userdetails)
  }

  updatedealeradmin(userdetails:any,did:number){
    return this.http.put(`${this.url}/agrodealerdealers/admin/editdet/${did}`,userdetails)
  }

  getfarmerposts(fid:any){
    return this.http.get(`${this.url}/agroadmin/admin/cropslist/farmerid=${fid}`).pipe(map((res:any)=>{
      if(res!=null){
        return res;  
      }else{
        res="no data found"
        return res;
      }
    }));
  }

  deletefarmer(fid:number){
    return this.http.delete(`${this.url}/agrofarmer/farmers/deleteid/${fid}`)
  }

  deletedealer(dealid:number){
    return this.http.delete(`${this.url}/agrodealer/dealers/admin/deletedealer/${dealid}`)
  }
}
