import {Component, OnInit} from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { BookingServicesService } from 'src/app/Services/booking-services.service';

import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-holidays',
  templateUrl: './holidays.component.html',
  styleUrls: ['./holidays.component.scss']
})
export class HolidaysComponent implements OnInit{

  holidays: any;
  roomTypes: any;
  formattedFromDate: string = '';
  formattedToDate: string = '';

  constructor(private service: BookingServicesService, private datePipe: DatePipe){}

  ngOnInit(): void {
    this.getRoomTypes();
  }

  getRoomTypes() {
    this.service.getRoomTypes().subscribe((res: any) => {
      if(res.status == 'success') {
        this.roomTypes = res.content;
        this.getHolidays();

      }
    })
  }
  value:any = '';

  displayedColumns: string[] = ['holiday', 'dates', 'room_Type', 'surge_fees'];
  dataSource = new MatTableDataSource();

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();  
  }

  onDateRangeSelected(event: MatDatepickerInputEvent<Date>, row: any, isStartDate: boolean): void {
    if (isStartDate) {
      row.dates.start = event.value as Date;
    } else {
      row.dates.end = event.value as Date;
    }
  }

  getHolidays() {
    this.service.getAllHolidays().subscribe((res: any) => {
      if(res.status == 'success') {
        this.holidays = res.content;
        this.dataSource = new MatTableDataSource(this.holidays);
      }
    })
  }

  addNewHoliday() {
    let newHoliday: any = {
      holidayName: "",
      roomType:{
          roomTypeId:1
      },
      fromDate:"",
      toDate:"",
      surgeFeePercentage: 0
    }
    this.holidays.push(newHoliday);
    this.dataSource = new MatTableDataSource(this.holidays);
  }

  updateFromDate(element: any) {
    if (element.fromDate) {
      element.fromDate = this.datePipe.transform(element.fromDate, 'yyyy-MM-dd') || '';
    }
  }

  updateToDate(element: any) {
    if (element.toDate) {
      element.toDate = this.datePipe.transform(element.toDate, 'yyyy-MM-dd') || '';
    }
  }

  saveUpdate() {
    this.dataSource.data.forEach((dateObj:any) => {
      dateObj.fromDate = this.convertToDateOnly(dateObj.fromDate);
      dateObj.toDate = this.convertToDateOnly(dateObj.toDate);
    });

    this.service.addHolidays(this.dataSource.data).subscribe((res: any) => {
      if(res.status == 'success') {
        alert('Holidays List Updated')
      }
      else {
        alert('Update Failed')
      }
    })
    
  }

  convertToDateOnly(dateString: string): string {
    const date = new Date(dateString);
    
    // Check if the date is valid and not NaN
    if (!isNaN(date.getTime())) {
      const year = date.getFullYear();
      let month = (date.getMonth() + 1).toString().padStart(2, '0');
      let day = date.getDate().toString().padStart(2, '0');
      return `${year}-${month}-${day}`;
    }
    
    // Return the original date string if it's not convertible
    return dateString;
  }
}


