import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router, NavigationStart } from '@angular/router';
import { Subscription } from 'rxjs';
import { LoginscreenComponent } from 'src/app/Components/loginscreen/loginscreen.component';
import { RegisterScreenComponent } from 'src/app/Components/register-screen/register-screen.component';
import { AuthServicesService } from 'src/app/Services/auth-services.service';

export let browserRefresh = false;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  subscription: Subscription | undefined;
  refreshSubscription: Subscription | undefined;
  browserRefresh!: boolean;
  user: any;

  constructor(public dialog: MatDialog, private router: Router, private service: AuthServicesService){
    this.subscription = this.service.getUser().subscribe((res: any) => {
      this.user = res;
    })
    this.subscription = router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        browserRefresh = !router.navigated;
        if(browserRefresh) {
          this.user = localStorage.getItem('userDetails');
          this.user = JSON.parse(this.user);
        }
      }
  });
  }

  openLoginDialog() {
    const dialogRef = this.dialog.open(LoginscreenComponent, {
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  openRegisterDialog() {
    const dialogRef = this.dialog.open(RegisterScreenComponent, {
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  openAboutUs() {
    this.router.navigateByUrl('/aboutUs');
  }

  logout() {
    localStorage.clear();
    this.service.clearData();
    this.router.navigateByUrl('/');
  }

  navigateToHome() {
    if(this.user?.role[0].authority === 'employee'){
      this.router.navigateByUrl('/staff');
    }
    else {
      this.router.navigateByUrl('/');
    }
  }

}
