import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CancelationPolicyComponent } from 'src/app/Components/cancelation-policy/cancelation-policy.component';
import { HotelPolicyComponent } from 'src/app/Components/hotel-policy/hotel-policy.component';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {

  constructor(private dialog: MatDialog){}

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

}
