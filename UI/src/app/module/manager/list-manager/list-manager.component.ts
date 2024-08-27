import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ManagerService } from '../../../service/manager/manager.service';

@Component({
  selector: 'app-list-manager',
  templateUrl: './list-manager.component.html',
  styleUrl: './list-manager.component.css',
  standalone:true,
  imports:[RouterLink]
})
export class ListManagerComponent implements OnInit{
  mngrList!:any;
  constructor(private mngrListService:ManagerService){}
  ngOnInit(): void {
      this.getMngrList();
  }
  getMngrList():void{
    this.mngrListService.managerList().subscribe((res)=>{
      this.mngrList=res;
    })
  }
}
