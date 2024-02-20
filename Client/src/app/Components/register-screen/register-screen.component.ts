import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthServicesService } from 'src/app/Services/auth-services.service';
import { emailValidator } from 'src/app/Directives/email-validator.directive';
import { jwtDecode } from "jwt-decode";

interface IUser {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
  dob: Date;
  phoneNo: string;
  address: string;
  gender: string;
}

@Component({
  selector: 'app-register-screen',
  templateUrl: './register-screen.component.html',
  styleUrls: ['./register-screen.component.scss']
})
export class RegisterScreenComponent implements OnInit{

  reactiveForm!: FormGroup;
  user: IUser;
  passwordCheck: boolean = false;
  maxDob!: Date;

  constructor(
    public dialogRef: MatDialogRef<RegisterScreenComponent>,
    public authService: AuthServicesService
  ) {
    this.user = {} as IUser;
    const today = new Date();
    this.maxDob = new Date(
    today.getFullYear() - 18,
    today.getMonth(),
    today.getDate()
  );
  }


  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.reactiveForm = new FormGroup({
      firstName: new FormControl(this.user.firstName, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50)
      ]),
      lastName: new FormControl(this.user.lastName, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50)
      ]),
      dob: new FormControl(this.user.dob, [
        Validators.required
      ]),
      gender: new FormControl(this.user.gender, [
        Validators.required
      ]),
      address: new FormControl(this.user.address, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(250)
      ]),
      email: new FormControl(this.user.email, [
        Validators.required,
        emailValidator()
      ]),
      phoneNo: new FormControl(this.user.phoneNo, [
        Validators.required,
        Validators.pattern('[0-9]{11}')
      ]),
      password: new FormControl(this.user.password, [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(250)
      ]),
      confirmPassword: new FormControl(this.user.confirmPassword, [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(250)
      ])
    })
  }

  onInputChange(event: any) {
    const pattern = /^[0-9]*$/;
    if (!pattern.test(event.target.value)) {
      event.target.value = event.target.value.replace(/[^0-9]/g, '');
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  signIn(): void {

    if (this.reactiveForm.invalid) {
      for (const control of Object.keys(this.reactiveForm.controls)) {
        this.reactiveForm.controls[control].markAsTouched();
      }
      return;
    }

    let payload = this.reactiveForm.value;
    payload.dob = payload.dob.toISOString().split('T')[0];
    this.authService.register(payload).subscribe((res: any  ) => {
      if(res.status == 'success') {
        localStorage.setItem('token',res.content.accessToken);
        let userDetails:any = jwtDecode(res.content.accessToken);
        localStorage.setItem('userDetails', JSON.stringify(userDetails));
        this.authService.setUser(userDetails);
        this.dialogRef.close();
      }
      else {
        alert(res.statusMessage);
      }
    })
  }

  matchPassword(event: any) {
    if(event.target.value === this.reactiveForm.controls['password'].value) {
      this.passwordCheck = true;
    }
    else {
      this.passwordCheck = false;
    }
  }
}
