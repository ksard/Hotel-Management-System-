import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookingServicesService } from 'src/app/Services/booking-services.service';
import * as CryptoJS from 'crypto-js';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit{

  payment_mode: String="credit-debit";
  isPaymentSuccessfull = false;
  paymentForm! : FormGroup;
  bookingData : any;
  today! : Date;
  minExpiryDate: any;

  constructor(
    private service : BookingServicesService,
    private router : Router
    ) {
      this.today = new Date();
      const currentMonth = ('0' + (this.today.getMonth() + 1)).slice(-2);
      const currentYear = this.today.getFullYear();
      this.minExpiryDate = `${currentYear}-${currentMonth}`;
    }

  ngOnInit(): void {
    this.bookingData = sessionStorage.getItem('bookingData');
    this.bookingData = JSON.parse((CryptoJS.AES.decrypt(this.bookingData,'dostana')).toString(CryptoJS.enc.Utf8));
    this.initialisePaymentForm();
  }

  initialisePaymentForm() {
    this.paymentForm = new FormGroup({
      paymentType: new FormControl('credit-debit', [Validators.required]),
      creditCardNo : new FormControl('', [Validators.required]),
      expiryDate : new FormControl('', [Validators.required]),
      cvv : new FormControl('', [Validators.required]),
      iban : new FormControl(''),
      transactionStatus: new FormControl('Success'),
      transactionId : new FormControl(Math.floor(100000 + Math.random() * 900000))
    })
  }

  togglePaymentType(event: any) {
    this.payment_mode = event.target.value;
    if(this.payment_mode === 'net-banking') {
      this.paymentForm.controls['iban'].addValidators([
        Validators.required,
        Validators.pattern(/^([A-Z]{2}[0-9]{2})([A-Z0-9]{4}){4}$/)
      ]);
      this.paymentForm.get('iban')?.updateValueAndValidity();

      this.paymentForm.controls['creditCardNo'].clearValidators();
      this.paymentForm.get('creditCardNo')?.updateValueAndValidity();

      this.paymentForm.controls['expiryDate'].clearValidators();
      this.paymentForm.get('expiryDate')?.updateValueAndValidity();

      this.paymentForm.controls['cvv'].clearValidators();
      this.paymentForm.get('cvv')?.updateValueAndValidity();
    }

    else {
      this.paymentForm.controls['creditCardNo'].addValidators([
        Validators.required
      ]);
      this.paymentForm.get('creditCardNo')?.updateValueAndValidity();

      this.paymentForm.controls['cvv'].addValidators([
        Validators.required,
        Validators.pattern(/^[0-9]{3}$/)
      ]);
      this.paymentForm.get('cvv')?.updateValueAndValidity();

      this.paymentForm.controls['expiryDate'].addValidators([Validators.required]);
      this.paymentForm.get('expiryDate')?.updateValueAndValidity();

      this.paymentForm.controls['iban'].clearValidators();
      this.paymentForm.get('iban')?.updateValueAndValidity();
    }
  }

  makePayment() {
    let payment = {
      paymentType : this.paymentForm.controls['paymentType'].value,
      paymentSourceInfo : this.payment_mode === 'net-banking' ? this.paymentForm.controls['iban'].value : this.paymentForm.controls['creditCardNo'].value,
      transactionStatus:"Success",
      transactionId : this.paymentForm.controls['transactionId'].value
    }

    this.bookingData.booking.payment = payment;
    this.service.bookRoom(this.bookingData).subscribe((res: any) => {
      if(res.status === 'success' && res.content.bookingStatus === 'Confirmed')
        this.router.navigateByUrl('payment-success')

        else {
          alert('Booking was unsuccessful. Any amount debited will be refunded back to the original sources');
        }
    })
    
  }
}

