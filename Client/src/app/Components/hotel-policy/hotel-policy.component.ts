import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-hotel-policy',
  templateUrl: './hotel-policy.component.html',
  styleUrls: ['./hotel-policy.component.scss']
})
export class HotelPolicyComponent {
  constructor(public dialogRef: MatDialogRef<HotelPolicyComponent>){
    
  }

}
