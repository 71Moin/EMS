import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ManagerService } from '../../../service/manager/manager.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-manager',
  templateUrl: './view-manager.component.html',
  styleUrl: './view-manager.component.css',
  imports:[ReactiveFormsModule],
  standalone:true
})
export class ViewManagerComponent implements OnInit{

  detailMngrForm!:FormGroup;
  constructor(private fb:FormBuilder,
    private managerService:ManagerService,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar){}
  ngOnInit(): void {
    this.detailMngrForm=this.fb.group({
      id:[''],
      managerName:[''],
      phoneNumber:[''],
      email:['']
    })
    const id = +this.route.snapshot.params['id'];
    this.getMngrDetail(id);
  }
  getMngrDetail(id:number):void{
    this.managerService.viewManager(id).subscribe((res:any)=>{
      if(res){
        const data=res;
        this.detailMngrForm.patchValue({
          id:data.id,
          managerName:data.managerName,
          phoneNumber:data.phoneNumber,
          email:data.email
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
