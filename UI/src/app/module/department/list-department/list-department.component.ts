import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DateStrPipe } from "../../../common/pipe/date-str.pipe";
import { DepartmentService } from '../../../service/department/department.service';

@Component({
    selector: 'app-list-department',
    templateUrl: './list-department.component.html',
    styleUrl: './list-department.component.css',
    standalone: true,
    imports: [RouterLink, DateStrPipe]
})
export class ListDepartmentComponent implements OnInit{
  deptList!:any;
  constructor(private deptListService:DepartmentService){}
  ngOnInit(): void {
      this.getDeptList();
  }
  getDeptList():void{
    this.deptListService.departmentList().subscribe((res:any)=>{
      this.deptList=res;
    })
  }
}
