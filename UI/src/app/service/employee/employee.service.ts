import { Injectable } from '@angular/core';
import { environment } from '../../../environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Data } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl=environment.apiUrl;
  constructor(private http:HttpClient) { }
  employeeList(): Observable<Data[]> {
    //JSON server
    // const apiUrl = `${this.apiUrl}/employee/`;
    const apiUrl = `${this.apiUrl}/employee/list`;
    return this.http.get<Data[]>(apiUrl);
  }
  addEmployee(data:any): Observable<any>{
    // const formData = new FormData();
    // for (const key in data) {
    //   if (data.hasOwnProperty(key)) {
    //     formData.append(key, data[key]);
    //   }
    // }
    return this.http.post<any>(`${this.apiUrl}/employee/add`, data);
  }
  deleteEmployee(id:number): Observable<any> { 
    const apiUrl = `${this.apiUrl}/employee/delete/${id}`;
    return this.http.delete<any>(apiUrl);
  }
  viewEmployee(id:number):Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/employee/detail/${id}`);
  }
  editEmployee(data:any): Observable<any>{
    return this.http.put<any>(`${this.apiUrl}/employee/update`,data);
  }
}
