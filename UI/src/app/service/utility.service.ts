import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor() { } 
  dateFormate(date:Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
  dateToStr(date:number): string {
    const dt=date.toString();
    if(dt.length==0 || dt.length!==8)
        return '';
    const year =dt.substring(0,4);
    const month = dt.substring(4,6);
    const day = dt.substring(6);
    return `${year}-${month}-${day}`;
  }
  getFormValuesAsJSON(formData:any):any {
    const formValues: any = {};
    Object.keys(formData.controls).forEach(controlName => {
      const control = formData.get(controlName);
      if (control) {
        formValues[controlName] = control.value;
      }
    });
    return formValues;
  }
  employeeFormValidation():EmployeeForm{
    const formData:EmployeeForm={
      firstName:'',
      lastName:'',
      dob:'',
      gender:'',
      phnNumber:'',
      email:'',
      hireDate:'',
      jobTitle:'',
      departmentId:'',
      addressId:'',
      salary:'',
      managerId:'',
      empStatus:'',
      emergencyContactName:'',
      emergencyContactRelationship:'',
      emergencyContactPhoneNumber:'',
      imageFile:''
    };
    return formData;
  }
  dateToNum(value:string):number{
    if(value !==null || value!==undefined){
      const yr=value.substring(0,4);
      const mnt=value.substring(5,7);
      const day=value.substring(8);
      const dtNum=parseInt(`${yr}${mnt}${day}`);
      return dtNum;
    }
    return 0;
  }
}import { EmployeeForm } from './FormInterface/employee-form';

