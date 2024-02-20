import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { LoginscreenComponent } from '../Components/loginscreen/loginscreen.component';
import { MatDialog } from '@angular/material/dialog';

@Injectable()
export class CustomInterceptor implements HttpInterceptor {

  constructor(private router: Router, public dialog: MatDialog) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
     const localToken = localStorage.getItem('token');
     if(localToken)
        request = request.clone({ headers: request.headers.set('Authorization', 'Bearer ' + localToken) })
        return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          const url = request.url.toLowerCase();
          if (url.endsWith('/login')) {
            alert('Invalid credentials. Please try again.');
          }
          const dialogRef = this.dialog.open(LoginscreenComponent, {
          });
          //this.router.navigate(['/']);
        }
        return throwError(error);
      })
    );;
  }
}
