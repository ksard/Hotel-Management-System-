import { Component } from '@angular/core';
import {FormGroup, FormControl} from '@angular/forms';
import { Router } from '@angular/router';

const today = new Date();

@Component({
  selector: 'app-book-rooms',
  templateUrl: './book-rooms.component.html',
  styleUrls: ['./book-rooms.component.scss']
})
export class BookRoomsComponent {
  currentDate = today;
  adultValue: number = 1;
  childValue: number = 0;

  constructor(private router: Router) {}

  dates = new FormGroup({
    checkIn: new FormControl(this.currentDate),
    checkOut: new FormControl(this.currentDate),
  });

  checkAvailibility() {
    const payload : any = {
      checkInDate : this.dates.value.checkIn?.toISOString().split('T')[0],
      checkOutDate : this.dates.value.checkIn?.toISOString().split('T')[0],
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
