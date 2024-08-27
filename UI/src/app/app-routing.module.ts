import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from './service/Auth/auth-guard.service';
import { LoginComponent } from './components/login/login.component';
import { PageNotFoundComponent } from './components/pageNotFound/page-not-found/page-not-found.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

const routes: Routes = [
  { path: '',   redirectTo: '/login', pathMatch: 'full' },
  {
    path:'login',
    component:LoginComponent
  },
  {
    path:'dashboard',
    component:DashboardComponent,
    canActivate: [AuthGuardService],
    pathMatch:"full"
  },
  {
    path: 'employee', loadChildren:()=>import('./module/employee/employee-routing.module').then(m=>m.EmployeeRoutingModule),
    canActivate: [AuthGuardService],
  },
  {
    path: 'department', loadChildren:()=>import('./module/department/department-routing.module').then(m=>m.DepartmentRoutingModule),
    canActivate: [AuthGuardService],
  },
  {
    path: 'manager', loadChildren:()=>import('./module/manager/manager-routing.module').then(m=>m.ManagerRoutingModule),
    canActivate: [AuthGuardService],
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
