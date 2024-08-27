import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ManagerService } from '../../../service/manager/manager.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-manager',
  templateUrl: './edit-manager.component.html',
  styleUrl: './edit-manager.component.css',
  imports:[ReactiveFormsModule,CommonModule],
  standalone:true
})
export class EditManagerComponent {
  editMngrForm!:FormGroup;
  constructor(private fb:FormBuilder,
    private managerService:ManagerService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar){}
  ngOnInit(): void {
    this.editMngrForm=this.fb.group({
      id:[''],
      managerName:['',[Validators.required]],
      phoneNumber:['',[Validators.required,Validators.maxLength(10),Validators.minLength(10)]],
      email:['',[Validators.required]],
      userName:['']
    })
    const id = +this.route.snapshot.params['id'];
    this.getMngrDetail(id);
  }
  getMngrDetail(id:number):void{
    this.managerService.viewManager(id).subscribe((res:any)=>{
      if(res){
        const data=res;
        this.editMngrForm.patchValue({
          id:data.id,
          managerName:data.managerName,
          phoneNumber:data.phoneNumber,
          email:data.email,
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
    if(this.editMngrForm.valid){
      this.managerService.editManager(this.editMngrForm.value).subscribe((res:any)=>{
        if(res.status==200){
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          this.editMngrForm.reset();
          this.router.navigate(['manager']);
        }else{
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          
        }
      })
    }else{
      this.editMngrForm.markAllAsTouched();
    }
  }
  onCancel():void{
    window.history.back();
  }
}
