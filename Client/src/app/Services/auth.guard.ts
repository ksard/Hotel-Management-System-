import { CanActivate, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthServicesService } from './auth-services.service';
import { LoginscreenComponent } from '../Components/loginscreen/loginscreen.component';
import { MatDialog } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate {
  constructor(private auth: AuthServicesService, private router: Router, public dialog: MatDialog) {
    
  }
  canActivate() {
    if(this.auth.isLoggedIn()) {
      return true;
    }
    const dialogRef = this.dialog.open(LoginscreenComponent, {
    });
    return false;
  }
}
