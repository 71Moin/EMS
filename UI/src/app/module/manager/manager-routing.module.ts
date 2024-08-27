import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListEmployeeComponent } from '../employee/list-employee/list-employee.component';
import { ListManagerComponent } from './list-manager/list-manager.component';
import { AddManagerComponent } from './add-manager/add-manager.component';
import { ViewManagerComponent } from './view-manager/view-manager.component';
import { EditManagerComponent } from './edit-manager/edit-manager.component';

const routes: Routes = [
  {
    path:"",
    component:ListManagerComponent
  },
  {
    path:"add",
    component:AddManagerComponent
  },
  {
    path:"detail/:id",
    component:ViewManagerComponent
  },
  {
    path:"update/:id",
    component:EditManagerComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManagerRoutingModule { }
