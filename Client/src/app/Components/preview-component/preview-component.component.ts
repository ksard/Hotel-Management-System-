import { Component, OnInit } from '@angular/core';
import { CancelationPolicyComponent } from '../cancelation-policy/cancelation-policy.component';
import { HotelPolicyComponent } from '../hotel-policy/hotel-policy.component';
import { MatDialog } from '@angular/material/dialog';
import { BookingServicesService } from 'src/app/Services/booking-services.service';
import { Router } from '@angular/router';
import { GuestDetailsComponent } from '../guest-details/guest-details.component';
import * as CryptoJS from 'crypto-js';

export interface Gallery {
  cols: number;
  rows: number;
  image: string;
}

@Component({
  selector: 'app-preview-component',
  templateUrl: './preview-component.component.html',
  styleUrls: ['./preview-component.component.scss']
})
export class PreviewComponentComponent implements OnInit {

  roomData: any;
  totalRoomPrice: any = 0;
  totalServiceCharge: any = 0;
  gallery: Gallery[] = [
    {cols: 2, rows: 4, image: '../../../assets/RoomTypes/LuxurySuites/Bedroom.jpg'},
    {cols: 1, rows: 4, image: '../../../assets/RoomTypes/LuxurySuites/Bathroom.jpg'},
    {cols: 1, rows: 2, image: '../../../assets/RoomTypes/LuxurySuites/View.jpg'},
    {cols: 1, rows: 2, image: '../../../assets/RoomTypes/LuxurySuites/View.jpg'}
  ];

  constructor(private dialog: MatDialog, 
              private service: BookingServicesService,
              private router: Router) {}


  ngOnInit(): void {
    this.roomData = sessionStorage.getItem('roomData');
    this.roomData = JSON.parse((CryptoJS.AES.decrypt(this.roomData,'dostana')).toString(CryptoJS.enc.Utf8));
    this.getTotalBasePrice();
    
  }

  openPopupcancel(){
    const dialogRef = this.dialog.open(CancelationPolicyComponent);

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }
  openPopuphotel(){
    const dialogRef = this.dialog.open(HotelPolicyComponent);

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  openGuestDetailsDialog(): void {
    const dialogRef = this.dialog.open(GuestDetailsComponent, {
      width: '4000px',
      data: { fname: '', lname: '', dob: '', gender: '', id_proof: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  payNow() {
    this.router.navigateByUrl('/payment');
  }

  //fetch icon from services data
  getIcon(id: any) {
    let services = this.roomData.servicesData;
    let index = services.findIndex((x:any) => x.serviceId == id);
    return services[index].serviceIcon;
  }

  getToolTip(id: any) {
    let services = this.roomData.servicesData;
    let index = services.findIndex((x:any) => x.serviceId == id);
    return services[index].serviceName;
  }

  getRoomPrice(room: any) {
    let totalPrice = (room.basePrice + room.totalServicePrice)*room.selectedCount;
    return totalPrice;
  }

  getTotalBasePrice() {
    this.roomData.selectedRoomData.forEach((room:any) => {
      let totalPrice = (room.basePrice + room.totalServicePrice) * room.selectedCount;
      this.totalRoomPrice = this.totalRoomPrice + totalPrice;
    });
    this.totalServiceCharge = (this.totalRoomPrice/100) * 9;
    return this.totalRoomPrice;
  }

  getRows(arr: number[], size: number): number[][] {
    const rows = [];
    for (let i = 0; i < arr.length; i += size) {
      rows.push(arr.slice(i, i + size));
    }
    return rows;
  }
}