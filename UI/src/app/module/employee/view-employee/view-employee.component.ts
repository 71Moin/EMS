import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { EmployeeService } from "../../../service/employee/employee.service";
import { UtilityService } from "../../../service/utility.service";
import { CommonModule } from "@angular/common";
import { MatSnackBar } from "@angular/material/snack-bar";
import { DepartmentService } from "../../../service/department/department.service";
import { ManagerService } from "../../../service/manager/manager.service";


@Component({
  selector: 'app-view-employee',
  templateUrl: './view-employee.component.html',
  styleUrl: './view-employee.component.css',
  imports:[RouterLink,ReactiveFormsModule,CommonModule],
  standalone:true
})
export class ViewEmployeeComponent implements OnInit{
  detailsEmpForm!:FormGroup;
  departments!:any;
  managers!:any; 
  constructor(private fb:FormBuilder,
    private employeeService:EmployeeService,
    private util:UtilityService,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private deptService:DepartmentService,
    private mangerService:ManagerService,
  ){}
  ngOnInit(): void {
    this.detailsEmpForm=this.fb.group({
      id:[''],
      firstName:[''],
      lastName:[''],
      dob:[''],
      gender:[''],
      phnNumber:[''],
      email:[''],
      hireDate:[''],
      jobTitle:[''],
      salary:[''],
      emergencyContactName:[''],
      emergencyContactRelationship:[''],
      emergencyContactPhoneNumber:[''],
      departmentName:[''],
      assignedManager:[''] 
    })
    const id = +this.route.snapshot.params['id'];
    this.getEmpDetail(id);
    this.getdeptList();
    this.getmngrList();
  }
  getEmpDetail(id:number):void{
    this.employeeService.viewEmployee(id).subscribe((res:any)=>{
      if(res){
        const data=res;
        this.detailsEmpForm.patchValue({
          id:data.id,
          firstName:data.firstName,
          lastName:data.lastName,
          dob:this.util.dateToStr(data.dob),
          gender:data.gender,
          phnNumber:data.phnNumber,
          email:data.email,
          hireDate:this.util.dateToStr(data.hireDate),
          jobTitle:data.jobTitle,
          salary:data.salary,
          emergencyContactName:data.emergencyContactName,
          emergencyContactRelationship:data.emergencyContactRelationship,
          emergencyContactPhoneNumber:data.emergencyContactPhoneNumber,
          departmentName:data.departmentName,
          assignedManager:data.assignedManager
        })
      }else{
        this.snackBar.open("Data Not Found", 'Cancel', {
          duration: 5000,
          panelClass: ['snackBarColor'],
        });
      }
    })
  }
  getdeptList():void{
    this.deptService.departmentList().subscribe((res:any)=>{
      this.departments=res;
    })
  }
  getmngrList():void{
    this.mangerService.managerList().subscribe((res:any)=>{
      this.managers=res;
    })
  }
  onCancel(): void {
    window.history.back();
  }
       
}
