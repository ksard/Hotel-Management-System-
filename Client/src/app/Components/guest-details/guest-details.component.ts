import { Component, Inject, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import * as CryptoJS from 'crypto-js';
import { MatDatepicker } from '@angular/material/datepicker';
import { emailValidator } from 'src/app/Directives/email-validator.directive';
import { ToastrService } from 'ngx-toastr';

interface Adult {
  firstName: string;
  lastName: string;
  dob: Date | null; // Consider using appropriate data type for DOB
  gender: string;
  identityProofType: string;
  identityProof: string;
}

interface IUser {
  firstName: string;
  lastName: string;
  email: string;
  dob: Date;
  phoneNo: string;
  address: string;
  gender: string;
  identityProofType: string;
  identityProof: string;
}

@Component({
  selector: 'app-guest-details',
  templateUrl: './guest-details.component.html',
  styleUrls: ['./guest-details.component.scss']
})
export class GuestDetailsComponent implements OnInit {

  adults: Adult[] = [];
  roomData: any;
  reactiveForm!: FormGroup;
  user: IUser;
  @ViewChild(MatDatepicker) dobPicker!: MatDatepicker<any>;
  isPrimaryGuest: boolean = false;
  addOnFormValid: boolean = false;
  loggedInUser!: any;
  totalRoomPrice: any = 0;
  totalServiceCharge: number =0;
  maxDob!: Date;
  error!: boolean;

  constructor(
    public dialogRef: MatDialogRef<GuestDetailsComponent>,
    private router: Router,
    private toastr: ToastrService
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
    this.roomData = sessionStorage.getItem('roomData');
    this.roomData = JSON.parse((CryptoJS.AES.decrypt(this.roomData,'dostana')).toString(CryptoJS.enc.Utf8));
    
    this.loggedInUser = localStorage.getItem('userDetails');
    this.loggedInUser = JSON.parse(this.loggedInUser);
    this.addAdults(this.roomData.enquiryDetails.adults - 1);
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
      identityProofType: new FormControl(this.user.gender, [
        Validators.required
      ]),
      identityProof: new FormControl(this.user.gender, [
        Validators.required
      ]),
    })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onInputChange(event: any) {
    const pattern = /^[0-9]*$/;
    if (!pattern.test(event.target.value)) {
      event.target.value = event.target.value.replace(/[^0-9]/g, '');
    }
  }

  togglePrimaryGuest() {
    this.isPrimaryGuest = !this.isPrimaryGuest;
  }

  validateAndPay() {
    this.validateAdditionalGuests();
    if(this.isPrimaryGuest || this.reactiveForm.valid) {
      if(this.addOnFormValid) {
        this.createBookingPayload();
      }
    }
    else {
      this.toastr.error("Please enter the mandatory fields")
    }
  }

  // Method to add adults dynamically based on the number provided
  addAdults(numAdults: number) {
    this.adults = new Array(numAdults).fill({}).map(() => ({
      firstName: '',
      lastName: '',
      dob: null,
      gender: '',
      identityProofType: '',
      identityProof: ''
    }));
  }

  validateAdditionalGuests() {
    if(this.roomData.enquiryDetails.adults - 1 === 0){
      this.addOnFormValid =  true;
    }

    else {
      this.adults.forEach((guest: any) => {
        if(guest.firstName.trim() == '' || guest.lastName.trim() == '' || guest.dob == null || guest.gender.trim() == '' || guest.identityProof.trim() == '' || guest.identityProofType.trim() == '') {
          this.addOnFormValid =  false;
        }
        else 
          this.addOnFormValid =  true;
      })
    }
  }

  getTotalBasePrice() {
    this.roomData.selectedRoomData.forEach((room:any) => {
      let totalPrice = room.basePrice + room.totalServicePrice;
      this.totalRoomPrice = this.totalRoomPrice + totalPrice;
    });
    this.totalServiceCharge = (this.totalRoomPrice/100) * 9;
  }

  assignPrimaryGuests() {
    let guest:any = {};
    if(this.isPrimaryGuest) {
      guest['personId'] = this.loggedInUser.personId;
    }

    else {
      guest = this.reactiveForm.value;
    }

    return guest;
  }

  createBookingPayload() {
    this.getTotalBasePrice();
    let payload = {}
    let roomTypesSelected: any = {}
    this.roomData.selectedRoomData.forEach((room: any) => {
      roomTypesSelected[room.roomTypeId] = room.selectedCount;
    });
    let guest = this.assignPrimaryGuests();
    payload = {
      "booking":{           
        "guest": guest ,
        
        "optedServices": JSON.stringify(this.roomData.selectedRoomData[0].includedServices),
        "roomPrice": this.totalRoomPrice,
        "totalPrice": this.totalRoomPrice + this.totalServiceCharge,
        "checkInDate": this.roomData.enquiryDetails.checkInDate,
        "checkOutDate": this.roomData.enquiryDetails.checkOutDate,
        "numOfGuest": this.roomData.enquiryDetails.adults,
        "additionalGuests": this.adults
        },
        "roomsSelected": roomTypesSelected
    }

    let bookingData = JSON.stringify(payload);
    let encryptedObject = CryptoJS.AES.encrypt(bookingData,'dostana').toString();
    sessionStorage.setItem('bookingData', encryptedObject);
    this.dialogRef.close();
    this.router.navigateByUrl('payment');
  }

}



