import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { DepartmentService } from '../../../service/department/department.service';

@Component({
  selector: 'app-view-department',
  templateUrl: './view-department.component.html',
  styleUrl: './view-department.component.css',
  imports:[ReactiveFormsModule],
  standalone:true
})
export class ViewDepartmentComponent implements OnInit{

  detailsDeptForm!:FormGroup;
  constructor(private fb:FormBuilder,
    private route: ActivatedRoute,
    private deptService:DepartmentService,
    private snackBar: MatSnackBar){}
  ngOnInit(): void {
    this.detailsDeptForm=this.fb.group({
      id:[''],
      departmentName:[''],
      location:[''],
    })
    const id = +this.route.snapshot.params['id'];
    this.getDeptDetail(id);
  }

  getDeptDetail(id:number):void{
    this.deptService.viewDepartment(id).subscribe((res:any)=>{
      if(res){
        const data=res;
        this.detailsDeptForm.patchValue({
          id:data.id,
          departmentName:data.departmentName,
          location:data.location,
        })
      }else{
        this.snackBar.open("Data Not Found", 'Cancel', {
          duration: 5000,
          panelClass: ['snackBarColor'],
        });
      }
    })
  }
  onCancel():void{
    window.history.back();
  }
}
