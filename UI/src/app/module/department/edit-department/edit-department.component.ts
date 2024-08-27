import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DepartmentService } from '../../../service/department/department.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-department',
  templateUrl: './edit-department.component.html',
  styleUrl: './edit-department.component.css',
  imports:[ReactiveFormsModule,CommonModule],
  standalone:true
})
export class EditDepartmentComponent implements OnInit{

  editDeptForm!:FormGroup;
  constructor(private fb:FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private deptService:DepartmentService,
    private snackBar: MatSnackBar){}
  ngOnInit(): void {
    this.editDeptForm=this.fb.group({
      id:[''],
      departmentName:['',[Validators.required]],
      location:['',[Validators.required]],
      userName:['']
    })
    const id = +this.route.snapshot.params['id'];
    this.getDeptDetail(id);
  }

  getDeptDetail(id:number):void{
    this.deptService.viewDepartment(id).subscribe((res:any)=>{
      if(res){
        const data=res;
        this.editDeptForm.patchValue({
          id:data.id,
          departmentName:data.departmentName,
          location:data.location,
          userName:data.userName
        })
      }else{
        this.snackBar.open("Data Not Found", 'Cancel', {
          duration: 5000,
          panelClass: ['snackBarColor'],
        });
      }
    })
  }
  onSubmit():void{
    if(this.editDeptForm.valid){
      this.deptService.editDepartment(this.editDeptForm.value).subscribe((res:any)=>{
        if(res.status==200){
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          this.editDeptForm.reset();
          this.router.navigate(['department']);
        }else{
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
        }
      })
    }else{
      this.editDeptForm.markAllAsTouched();
    }
  }
  onCancel():void{
    window.history.back();
  }
}
