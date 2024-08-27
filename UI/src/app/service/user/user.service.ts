import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl=environment.apiUrl;
  private userName: string | null = null;
  constructor(private http:HttpClient) { }
  addUser(data:any): Observable<any>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(`${this.apiUrl}/ems/register`, data,{ headers });
  }
  userLogin(data:any): Observable<any>{
    return this.http.post<any>(`${this.apiUrl}/ems/login`, data,{});
  }
  updatePassword(data:any): Observable<any>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(`${this.apiUrl}/ems/updPass`, data,{ headers });
  }
  public setUserName(userName: string): void {
    this.userName = userName;
  }

  public getUserName(): string | null {
    return this.userName;
  }

  public clearUserName(): void {
    this.userName = null;
  }
}
