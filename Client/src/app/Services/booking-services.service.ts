import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookingServicesService {

  apiUrl: string = environment.apiUrl

  constructor(private http : HttpClient) { }

  bookRoom(bookingData: any) {
    return this.http.post(this.apiUrl + 'room/book', bookingData);
  }

  getRoomDetails(availability: any) {
    return this.http.post(this.apiUrl + 'rooms', availability);
  }

  getAllBookings() {
    return this.http.get(this.apiUrl + 'room/bookings');
  }

  getAllServices() {
    return this.http.get(this.apiUrl + 'rooms/services');
  }

  addServices(serviceList: any) {
    return this.http.post(this.apiUrl + 'room/services', serviceList);
  }

  checkInRoom(id: number) {
    let url = this.apiUrl + `room/checkin/${id}`;
    return this.http.post(url, {});
  }

  checkOutRoom(id: number) {
    let url = this.apiUrl + `room/checkout/${id}`;
    return this.http.post(url, {});
  }

  cancelBooking(id: number) {
    let url = this.apiUrl + `room/cancel/${id}`;
    return this.http.post(url, {});
  }

  getAllHolidays(){
    return this.http.get(this.apiUrl + 'employee/holidays');
  }

  getRoomTypes() {
    return this.http.get(this.apiUrl + 'rooms/types');
  }

  addHolidays(holidays: any) {
    return this.http.post(this.apiUrl + 'employee/holidays', holidays);
  }

}
