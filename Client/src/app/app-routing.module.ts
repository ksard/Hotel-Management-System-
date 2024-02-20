import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeScreenComponent } from './Components/home-screen/home-screen.component';
import { ViewRoomsComponent } from './Components/view-rooms/view-rooms.component';
import { AboutUsComponent } from './Components/about-us/about-us.component';
import { PreviewComponentComponent } from './Components/preview-component/preview-component.component';
import { PaymentComponent } from './Components/payment/payment.component';
import { StaffDashboardComponent } from './Components/staff-dashboard/staff-dashboard.component';
import { PaymentSuccessComponent } from './Components/payment-success/payment-success.component';
import { AuthGuard } from './Services/auth.guard';
import { RoleGuard } from './Services/role.guard';

const routes: Routes = [
  
  {
    path: '',
    component: HomeScreenComponent
  },
  {
    path: 'viewRooms',
    component: ViewRoomsComponent
  },
  {
    path: 'aboutUs',
    component: AboutUsComponent
  },
  {
    path: 'preview-booking',
    component: PreviewComponentComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'payment',
    component: PaymentComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'staff',
    component: StaffDashboardComponent,
    canActivate: [RoleGuard]
  },
  {
    path: 'payment-success',
    component: PaymentSuccessComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
