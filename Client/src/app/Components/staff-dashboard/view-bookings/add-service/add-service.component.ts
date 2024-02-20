import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {MatTableModule} from '@angular/material/table';
import { BookingServicesService } from 'src/app/Services/booking-services.service';

export interface ServiceTable {
  serviceIcon: string
  serviceName: string;
  price: number;
  checkBox: boolean;
}

@Component({
  selector: 'app-add-service',
  templateUrl: './add-service.component.html',
  styleUrls: ['./add-service.component.scss']
})
export class AddServiceComponent implements OnInit {
  checked = false;
  indeterminate = false;
  labelPosition: 'before' | 'after' = 'after';
  disabled = false;
  displayedColumns: string[] = ['serviceIcon','serviceName', 'price', 'checkBox'];
  dataSource: any;
  addedServices:any = {
    services: []
  };

  constructor(
    public dialogRef: MatDialogRef<AddServiceComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private services: BookingServicesService
  ) {}
  ngOnInit(): void {
    this.setDataSource();
  }

  setDataSource() {
    if(this.data.booking.optedServices) {
      this.addedServices.services = this.data.booking.optedServices.split(',').filter((num: any) => !isNaN(num));
      this.dataSource = this.data.services.filter((item: any) => {
        return !this.addedServices.services.includes(item.serviceId.toString())
      });
    }
    else {
      this.dataSource = this.data.services;
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  updateService(service: any) {
    let index = this.addedServices.services.indexOf(service.serviceId);
    if(index >= 0) {
      this.addedServices.totalServicePrice = this.addedServices.totalServicePrice - service.price;
      this.addedServices.services.splice(index);
    }
    else {
      this.addedServices.totalServicePrice = this.addedServices.totalServicePrice + service.price;
      this.addedServices.services.push(service.serviceId);
    }
  }

  addNewService() {
    let payload = {
      "bookingId": this.data.booking.bookingId,
      "optedServices": this.addedServices.services.toString()
  }
  this.services.addServices(payload).subscribe((res: any) => {
    if(res.status === 'success') {
      alert("Services added successfully!");
    }
    else {
      alert(res.statusMessage);
    }
  })
  }

  checkInRoom() {
    this.services.checkInRoom(this.data.booking.bookingId).subscribe((res: any) => {
      if(res.status === 'success') {
        alert("Check-In Successfull")
      }
      else {
        alert("Check-In Unsuccessful")
      }
    })
  }

  checkOutRoom() {
    this.services.checkOutRoom(this.data.booking.bookingId).subscribe((res: any) => {
      if(res.status === 'success') {
        alert("Check-Out Successfull");
        this.dialogRef.close();
      }
      else {
        alert("Check-Out Unsuccessful")
      }
    })
  }

  cancelRoom() {
    this.services.cancelBooking(this.data.booking.bookingId).subscribe((res: any) => {
      if(res.status === 'success') {
        alert("Booking Cancelled");
        this.dialogRef.close();
      }
      else {
        alert("Cancellation Unsuccessfull")
      }
    })
  }
}
