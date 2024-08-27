import { Injectable } from '@angular/core';
import { environment } from '../../../environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticatedKey = 'isAuthenticated';
  private apiUrl=environment.apiUrl;
  constructor(private http:HttpClient) { }
  setAuthenticationStatus(isAuthenticated: boolean): void {
    localStorage.setItem(this.isAuthenticatedKey, isAuthenticated.toString());
  }
  isLoggedIn(): boolean {
    const isAuthenticated = localStorage.getItem(this.isAuthenticatedKey);
    return isAuthenticated ? JSON.parse(isAuthenticated) : false;
  }
  sendOtp(userForm:any):Observable<any>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(`${this.apiUrl}/otp/send`, userForm,{headers});
  }
  validateOtp(userForm:any):Observable<JSON>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(`${this.apiUrl}/otp/validate`, userForm,{headers});
  }
  forgotOtpSend(userForm:any):Observable<any>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(`${this.apiUrl}/otp/forgotOtpSend`, userForm,{headers});
  }
  validateForgotOtp(userForm:any):Observable<JSON>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(`${this.apiUrl}/otp/validateForgotOtp`, userForm,{headers});
  }
}
