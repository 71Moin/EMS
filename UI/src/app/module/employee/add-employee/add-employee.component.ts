import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { EmployeeService } from "../../../service/employee/employee.service";
import { UtilityService } from "../../../service/utility.service";
import { CommonModule } from "@angular/common";
import { DepartmentService } from "../../../service/department/department.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router, RouterLink } from "@angular/router";
import { ManagerService } from "../../../service/manager/manager.service";


@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrl: './add-employee.component.css',
  imports:[ReactiveFormsModule,CommonModule,RouterLink],
  standalone:true
})
export class AddEmployeeComponent implements OnInit{
  addEmpForm!:FormGroup;
  departments!:any;
  managers!:any; 
  constructor(private fb:FormBuilder,
    private employeeService:EmployeeService,
    private util:UtilityService,
    private deptService:DepartmentService,
    private mangerService:ManagerService,
    private snackBar: MatSnackBar,
    private router: Router,

  ){}
  ngOnInit(): void {
      this.addEmpForm=this.fb.group({
        firstName:['',[Validators.required,Validators.maxLength(50)]],
        lastName:['',[Validators.required,Validators.maxLength(50)]],
        dob:['',[Validators.required]],
        gender:['',[Validators.required]],
        phnNumber:['',[Validators.required,Validators.maxLength(10),Validators.minLength(10)]],
        email:['',[Validators.required]],
        hireDate:['',[Validators.required]],
        jobTitle:['',[Validators.required]],
        salary:['',[Validators.required]],
        emergencyContactName:[''],
        emergencyContactRelationship:[''],
        emergencyContactPhoneNumber:['',[Validators.required,Validators.maxLength(10),Validators.minLength(10)]],
        departmentName:['',[Validators.required]],
        assignedManager:['']
      })
      this.getdeptList();
      this.getmngrList();
  }
  onSubmit():void{
    if(this.addEmpForm.valid){
      const dob=this.addEmpForm.get('dob')?.value;
      const hrdt=this.addEmpForm.get('hireDate')?.value;
      this.addEmpForm.patchValue({
        dob:this.util.dateToNum(dob),
        hireDate:this.util.dateToNum(hrdt)
      })
      //console.log(this.addEmpForm.value);
      this.employeeService.addEmployee(this.addEmpForm.value)
      .subscribe((res:any)=>{
        if(res.status==200){
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          this.addEmpForm.reset();
          this.router.navigate(['employee']);
        }else{
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          this.addEmpForm.patchValue({
            dob:dob,
            hireDate:hrdt
          })
        }
      })
    }else{
      this.addEmpForm.markAllAsTouched();
    }
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
