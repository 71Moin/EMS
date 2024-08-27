import { Component, OnInit } from '@angular/core';
import { Data, RouterLink } from '@angular/router';
import { EmployeeService } from '../../../service/employee/employee.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { DateStrPipe } from '../../../common/pipe/date-str.pipe';
@Component({
  selector: 'app-list-employee',
  templateUrl: './list-employee.component.html',
  styleUrl: './list-employee.component.css',
  standalone:true,
  imports:[RouterLink,CommonModule,DateStrPipe]
})
export class ListEmployeeComponent implements OnInit{
  empList!:any;
  constructor(private empListService:EmployeeService){}
  ngOnInit(): void {
      this.getEmpList();
  }
  getEmpList():void{
    this.empListService.employeeList().subscribe((res:any)=>{
      this.empList=res;
      //console.log(this.empList);
    })
  }
}
