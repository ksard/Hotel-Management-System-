import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginscreenComponent } from './Components/loginscreen/loginscreen.component';
import { RegisterScreenComponent } from './Components/register-screen/register-screen.component';
import { HomeScreenComponent } from './Components/home-screen/home-screen.component';
import { HeaderComponent } from './Common/header/header.component';
import { FooterComponent } from './Common/footer/footer.component';
import { PaymentComponent } from './Components/payment/payment.component';
import { AboutUsComponent } from './Components/about-us/about-us.component';
import { ViewRoomsComponent } from './Components/view-rooms/view-rooms.component';
import { DialogServicesComponent } from './Components/view-rooms/dialog-services/dialog-services.component';
import { PreviewComponentComponent } from './Components/preview-component/preview-component.component';
import { CancelationPolicyComponent } from './Components/cancelation-policy/cancelation-policy.component';
import { HotelPolicyComponent } from './Components/hotel-policy/hotel-policy.component';
import { HolidaysComponent } from './Components/staff-dashboard/holidays/holidays.component';
import { ViewBookingsComponent } from './Components/staff-dashboard/view-bookings/view-bookings.component';
import { StaffDashboardComponent } from './Components/staff-dashboard/staff-dashboard.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon';
import { MaterialModule } from './Modules/material.module';
import { GuestDetailsComponent } from './Components/guest-details/guest-details.component';
import { MatSelectModule } from '@angular/material/select';
import { PaymentSuccessComponent } from './Components/payment-success/payment-success.component';
import { EmailValidatorDirective } from './Directives/email-validator.directive';
import { BookRoomsComponent } from './Components/staff-dashboard/book-rooms/book-rooms.component';
import { ToastrModule } from 'ngx-toastr';
import { CustomInterceptor } from './Services/custom.interceptor';
import { AddServiceComponent } from './Components/staff-dashboard/view-bookings/add-service/add-service.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginscreenComponent,
    RegisterScreenComponent,
    HomeScreenComponent,
    HeaderComponent,
    FooterComponent,
    PaymentComponent,
    AboutUsComponent,
    ViewRoomsComponent,
    DialogServicesComponent,
    PreviewComponentComponent,
    CancelationPolicyComponent,
    HotelPolicyComponent,
    HolidaysComponent,
    ViewBookingsComponent,
    StaffDashboardComponent,
    GuestDetailsComponent,
    PaymentSuccessComponent,
    EmailValidatorDirective,
    BookRoomsComponent,
    AddServiceComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MaterialModule,
    CommonModule,
    HttpClientModule,
    MatSelectModule,
    ToastrModule.forRoot()
  ],
  providers: [DatePipe,{
    provide: HTTP_INTERCEPTORS, useClass: CustomInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
