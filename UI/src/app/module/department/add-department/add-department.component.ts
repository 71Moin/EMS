import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DepartmentService } from '../../../service/department/department.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-department',
  templateUrl: './add-department.component.html',
  styleUrl: './add-department.component.css',
  imports:[ReactiveFormsModule,CommonModule],
  standalone:true
})
export class AddDepartmentComponent implements OnInit{
  addDeptForm!:FormGroup;
  constructor(private fb:FormBuilder,
    private deptService:DepartmentService,
    private snackBar: MatSnackBar,
    private router: Router){}
  ngOnInit(): void {
    this.addDeptForm=this.fb.group({
      departmentName:['',[Validators.required]],
      location:['',[Validators.required]],
    })
  }
  onSubmit():void{
    if(this.addDeptForm.valid){
      this.deptService.addDepartment(this.addDeptForm.value).subscribe((res:any)=>{
        if(res.status==200){
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          this.addDeptForm.reset();
          this.router.navigate(['department']);
        }else{
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          
        }
      })
    }else{
      this.addDeptForm.markAllAsTouched();
    }
  }
  onCancel():void{
    window.history.back();
  }
}
