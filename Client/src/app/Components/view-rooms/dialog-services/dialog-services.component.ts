import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {MatTableModule} from '@angular/material/table';

export interface ServiceTable {
  serviceIcon: string
  serviceName: string;
  price: number;
  checkBox: boolean;
}

@Component({
  selector: 'app-dialog-services',
  templateUrl: './dialog-services.component.html',
  styleUrls: ['./dialog-services.component.scss']
})
export class DialogServicesComponent {
  checked = false;
  indeterminate = false;
  labelPosition: 'before' | 'after' = 'after';
  disabled = false;
  displayedColumns: string[] = ['serviceIcon','serviceName', 'price', 'checkBox'];
  dataSource: any;
  addedServices:any = {
    totalServicePrice: 0,
    services: [1,3,4]
  }

  constructor(
    public dialogRef: MatDialogRef<DialogServicesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.dataSource = data.filter((res:any) => {
      return (res.serviceId != 1 && res.serviceId !=3 && res.serviceId != 4 );
    })
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
}
