import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-cancelation-policy',
  templateUrl: './cancelation-policy.component.html',
  styleUrls: ['./cancelation-policy.component.scss']
})
export class CancelationPolicyComponent {
  constructor(public dialogRef: MatDialogRef<CancelationPolicyComponent>){
    
  }
}
