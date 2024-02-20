import { Component } from '@angular/core';
import {FormGroup, FormControl} from '@angular/forms';
import { Router } from '@angular/router';
import { BookingServicesService } from 'src/app/Services/booking-services.service';

const today = new Date();

@Component({
  selector: 'app-home-screen',
  templateUrl: './home-screen.component.html',
  styleUrls: ['./home-screen.component.scss']
})
export class HomeScreenComponent {

  currentDate = today;
  adultValue: number = 1;
  childValue: number = 0;

  constructor(
    private router: Router,
    private service: BookingServicesService
    ) {
      sessionStorage.clear();
    }

  dates = new FormGroup({
    checkIn: new FormControl(this.currentDate),
    checkOut: new FormControl(this.currentDate),
  });

  checkAvailibility() {
    const payload : any = {
      checkInDate : this.dates.value.checkIn?.toISOString().split('T')[0],
      checkOutDate : this.dates.value.checkOut?.toISOString().split('T')[0],
      adults : this.adultValue,
      child : this.childValue
    }
    sessionStorage.setItem('roomDetails', JSON.stringify(payload));
    this.router.navigateByUrl('/viewRooms');
  }

  checkAdultValue() {
    if(!this.adultValue || this.adultValue < 1) {
      this.adultValue = 1;
    }
  }

  checkChildValue() {
    if(!this.childValue || this.childValue < 0) {
      this.childValue = 0;
    }
  }
}
