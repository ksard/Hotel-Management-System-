import { CanActivate, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthServicesService } from './auth-services.service';

@Injectable({
  providedIn: 'root'
})

export class RoleGuard implements CanActivate {
  constructor(private auth: AuthServicesService, private router: Router) {
    
  }
  canActivate() {
    let role = ''
    if(this.auth.isLoggedIn()) {
      let loggedInUser:any = localStorage.getItem('userDetails');
      loggedInUser = JSON.parse(loggedInUser);
      role = loggedInUser.role[0].authority;

      if(role === 'employee') {
        return true
      }
    }
    
    this.router.navigate(['']);
    return false;
  }
}