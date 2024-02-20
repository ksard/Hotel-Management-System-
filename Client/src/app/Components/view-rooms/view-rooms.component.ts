import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DialogServicesComponent } from './dialog-services/dialog-services.component';
import {  } from "@fortawesome/free-solid-svg-icons";
import { FormControl, FormGroup } from '@angular/forms';
import { BookingServicesService } from 'src/app/Services/booking-services.service';
import { ToastrService } from 'ngx-toastr';
import * as CryptoJS from 'crypto-js';

const today = new Date();

@Component({
  selector: 'app-view-rooms',
  templateUrl: './view-rooms.component.html',
  styleUrls: ['./view-rooms.component.scss']
})
export class ViewRoomsComponent implements OnInit {

  roomData: any;
  roomTypes: any;
  currentDate = today;
  adultValue: number = 1;
  childValue: number = 0;
  totalRoomsSelected = 0;
  bookingConditions: any = {
    maxRoomAllocation : 1,
    minRoomAllocation : 1
  }
  dates = new FormGroup({
    checkIn: new FormControl(this.currentDate),
    checkOut: new FormControl(this.currentDate),
  });
  finalServiceList: any;
  totalServicesPrice: any;

  constructor(private dialog: MatDialog, 
              private router: Router,
              private service: BookingServicesService,
              private toastr: ToastrService) {}

  ngOnInit(): void {
    let payload:any = sessionStorage.getItem('roomDetails');
    payload = JSON.parse(payload);
    this.service.getRoomDetails(payload).subscribe((res:any) => {
      if(res.status === 'success') {
        this.roomData = res;
        this.filterUniqueRooms();
        this.assignFormValues(payload);
      }
    })
  }

  assignFormValues(payload: any) {
    this.dates.get('checkIn')?.patchValue(payload.checkInDate);
    this.dates.get('checkOut')?.patchValue(payload.checkOutDate);
    this.adultValue = payload.adults;
    this.childValue = payload.child;
    this.defineBookingRules();
  }

  defineBookingRules() {
    this.bookingConditions.maxRoomAllocation = this.adultValue
    this.bookingConditions.minRoomAllocation = Math.ceil(this.adultValue/2);
    this.setRoomCapacities()
  }
  setRoomCapacities() {
    this.roomTypes.forEach((room:any) => {
      let keyValue = '('+ room.type +', ' +room.view+ ')';
      room.min = 0;
      room.max = this.roomData.roomsByTypeContent[keyValue] ? this.roomData.roomsByTypeContent[keyValue] : 0;
      room.selectedCount = 0;
    });
  }

  openServices() {
    const dialogRef = this.dialog.open(DialogServicesComponent, {
      data: this.roomData.servicesContent
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      this.finalServiceList = [...result.services];
      this.totalServicesPrice = result.totalServicePrice;
      this.createPreviewData();
    });
  }

  //method to filter unique rooms apart from floors
  filterUniqueRooms() {
    this.roomTypes = this.roomData.roomTypesContent;
    this.roomTypes.forEach((element: any) => {
      element.includedServices = JSON.parse("[" + element.includedServices + "]");
    });
  }

  //fetch icon from services data
  getIcon(id: any) {
    let services = this.roomData.servicesContent;
    let index = services.findIndex((x:any) => x.serviceId == id);
    return services[index].serviceIcon;
  }

  //plus and button minus
  changeCount(value: string, i: number) {
    if(value == 'plus') {
      if(this.checkTotalRoomsCount()) {
        if(this.roomTypes[i].selectedCount < this.roomTypes[i].max) {
          this.roomTypes[i].selectedCount = this.roomTypes[i].selectedCount + 1;
          this.totalRoomsSelected = this.totalRoomsSelected + 1
        }
        else {
          this.toastr.error("No more rooms available");
        }
      }
      else {
        this.toastr.error('You cannot add more than ' +this.bookingConditions.maxRoomAllocation+ ' rooms');
        
      }
    }

    else {
      if(this.roomTypes[i].selectedCount <= 0) {
        this.roomTypes[i].selectedCount = 0;
      }
      else {
        this.roomTypes[i].selectedCount = this.roomTypes[i].selectedCount - 1;
        this.totalRoomsSelected = this.totalRoomsSelected - 1;
      }
    }
  }

  checkTotalRoomsCount(): boolean {
    if(this.totalRoomsSelected >= this.bookingConditions.maxRoomAllocation) {
      return false;
    }
    return true;
  }

  checkBookingConditions() {
    if(this.totalRoomsSelected >= this.bookingConditions.minRoomAllocation && this.totalRoomsSelected <= this.bookingConditions.maxRoomAllocation) {
      return false;
    }
    return true;
  }

  //method to filter out the available rooms
  checkRoomCount(room: any) {
    let keyValue = '('+ room.type +', ' +room.view+ ')';
    if(this.roomData.roomsByTypeContent[keyValue] > 0) {
      return true
    }
    else {
      return false;
    }
  }

  checkAvailibility() {
    const payload : any = {
      checkInDate : this.dates.value.checkIn?.toISOString().split('T')[0],
      checkOutDate : this.dates.value.checkOut?.toISOString().split('T')[0],
      adults : this.adultValue,
      child : this.childValue
    }
    this.service.getRoomDetails(payload).subscribe((res:any) => {
      if(res.status === 'success') {
        this.roomData = res;
        this.resetValues();
        this.filterUniqueRooms();
        this.defineBookingRules();
      }
    })  
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

  createPreviewData() {
    let selectedRooms = [...this.roomTypes]
    selectedRooms = selectedRooms.filter((res: any) => {
      return res.selectedCount > 0;
    })
    selectedRooms.forEach((room: any) => {
      room.includedServices = [...this.finalServiceList];
      room.totalServicePrice = this.totalServicesPrice;
    });
    let enquiryDetails:any = sessionStorage.getItem('roomDetails');
    enquiryDetails = JSON.parse(enquiryDetails);
    let finalData: any = {
      selectedRoomData : selectedRooms,
      servicesData: this.roomData.servicesContent,
      enquiryDetails: enquiryDetails

    }
    finalData = JSON.stringify(finalData)
    let encryptedObject = CryptoJS.AES.encrypt(finalData,'dostana').toString();
    sessionStorage.setItem('roomData', encryptedObject);
    this.router.navigateByUrl('preview-booking');
  }

  resetValues() {
    this.totalRoomsSelected = 0;
  }

}
