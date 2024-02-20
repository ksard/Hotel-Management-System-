import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { BookingServicesService } from 'src/app/Services/booking-services.service';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { AddServiceComponent } from './add-service/add-service.component';

const ELEMENT_DATA: any[] = [];

/**
 * @title Table with filtering
 */

@Component({
  selector: 'app-view-bookings',
  templateUrl: './view-bookings.component.html',
  styleUrls: ['./view-bookings.component.scss']
})
export class ViewBookingsComponent implements OnInit {

  bookingsData: any;
  servicesData: any;
  displayedColumns: string[] = ['first_name', 'last_name', 'phone_no', 'checkIn', 'checkOut', 'roomCount', 'price', 'action'];
  dataSource: any;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private service: BookingServicesService, private dialog: MatDialog){}

  ngOnInit(): void {
    
    this.service.getAllServices().subscribe((res: any) => {
      if(res.status == 'success') {
        this.servicesData = res.content;
      }
    })

    this.service.getAllBookings().subscribe((res:any) => {
      if(res.status == 'success'){
        this.bookingsData = res.content;
        this.dataSource = new MatTableDataSource(this.bookingsData);
        this.dataSource.paginator = this.paginator;
      }
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  addService(booking: any) {
    const dialogRef = this.dialog.open(AddServiceComponent, {
      data: {
        'booking' : booking,
        'services' : this.servicesData
      }
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      this.service.getAllBookings().subscribe((res:any) => {
        if(res.status == 'success'){
          this.bookingsData = res.content;
          this.dataSource = new MatTableDataSource(this.bookingsData);
          this.dataSource.paginator = this.paginator;
        }
      })
    });
  }
}
