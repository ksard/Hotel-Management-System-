import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthServicesService } from 'src/app/Services/auth-services.service';
import { emailValidator } from 'src/app/Directives/email-validator.directive';
import { RegisterScreenComponent } from '../register-screen/register-screen.component';
import { jwtDecode } from "jwt-decode";
import { Router } from '@angular/router';

interface IUser {
  email: string;
  password: string;
}

@Component({
  selector: 'app-loginscreen',
  templateUrl: './loginscreen.component.html',
  styleUrls: ['./loginscreen.component.scss']
})
export class LoginscreenComponent implements OnInit {

  user: IUser;
  loginCreds!: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<LoginscreenComponent>,
    public service: AuthServicesService,
    public dialog: MatDialog,
    private router: Router
  ) {
    this.user = {} as IUser;
  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.loginCreds = new FormGroup ({
      email: new FormControl(this.user.email, [
        Validators.required,
        emailValidator()
      ]),
      password: new FormControl(this.user.password, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(250)
      ]),
    })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  goToRegister() {
    this.dialogRef.close();
    const dialog = this.dialog.open(RegisterScreenComponent, {
    });

    dialog.afterClosed().subscribe(result => {
      
    });
  }

  signIn() {
    this.service.login(this.loginCreds.value).subscribe((res: any) => {
        localStorage.setItem('token',res.accessToken);
        let userDetails:any = jwtDecode(res.accessToken);
        localStorage.setItem('userDetails', JSON.stringify(userDetails));
        this.service.setUser(userDetails);
        if(userDetails.role[0].authority === 'employee') {
          this.router.navigateByUrl('/staff')
        }
    })
    this.dialogRef.close();
  }
}
