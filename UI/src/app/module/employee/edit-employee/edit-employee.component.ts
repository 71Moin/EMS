import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { EmployeeService } from "../../../service/employee/employee.service";
import { UtilityService } from "../../../service/utility.service";
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { MatSnackBar } from "@angular/material/snack-bar";
import { DepartmentService } from "../../../service/department/department.service";
import { CommonModule } from "@angular/common";
import { ManagerService } from "../../../service/manager/manager.service";

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrl: './edit-employee.component.css',
  imports:[ReactiveFormsModule,RouterLink,CommonModule,FormsModule],
  standalone:true
})
export class EditEmployeeComponent implements OnInit{
  editEmpForm!:FormGroup;
  departments!:any;
  managers!:any;
  constructor(private fb:FormBuilder,
    private employeeService:EmployeeService,
    private util:UtilityService,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private router: Router,
    private deptService:DepartmentService,
    private mangerService:ManagerService,
  ){}
  ngOnInit(): void {
    this.editEmpForm=this.fb.group({
      id:[''],
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
      assignedManager:[''],
      empStatus:[''],
      userName:['']
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
        this.editEmpForm.patchValue({
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
          assignedManager:data.assignedManager,
          empStatus:data.empStatus,
          userName:data.userName
        })
      }else{
        this.snackBar.open("Data Not Found", 'Cancel', {
          duration: 5000,
          panelClass: ['snackBarColor'],
        });
      }
      //console.log(this.editEmpForm.value);
    })
  }
  onSubmit():void{
    if(this.editEmpForm.valid){
      const dob=this.editEmpForm.get('dob')?.value;
      const hrdt=this.editEmpForm.get('hireDate')?.value;
      this.editEmpForm.patchValue({
        dob:this.util.dateToNum(dob),
        hireDate:this.util.dateToNum(hrdt)
      })
      this.employeeService.editEmployee(this.editEmpForm.value)
      .subscribe((res:any)=>{
        if(res.status==200){
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          this.editEmpForm.reset();
          this.router.navigate(['employee']);
        }else{
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          this.editEmpForm.patchValue({
            dob:dob,
            hireDate:hrdt
          })
        }
      })
    }else{
      this.editEmpForm.markAllAsTouched();
    }
  }
  getdeptList(){
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
