import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthServicesService {
  
  apiUrl: string = environment.apiUrl;
  data = new Subject<any>();

  constructor(private http : HttpClient) { }

  isLoggedIn() {
    return !!localStorage.getItem('token');
  }

  register(userData: any) {
    return this.http.post(this.apiUrl + 'auth/register', userData)
  }

  login(userCreds: any) {
    return this.http.post(this.apiUrl + 'auth/login', userCreds)
  }

  setUser(user: any):void {
    this.data.next(user);
  }

  getUser(): Observable<{}> {
    return this.data.asObservable();
  }

  clearData() {
    this.data.next(null);
  }
}
