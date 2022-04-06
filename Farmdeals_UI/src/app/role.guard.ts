import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private login : LoginService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean 
    | UrlTree> 
    | Promise<boolean 
    | UrlTree> | boolean 
    | UrlTree {
      if(localStorage.getItem("role")==route.data['role']){
        return true
      }else{
    return false;
  }
  }
  
}
