import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateNum'
})
export class DateNumPipe implements PipeTransform {

  transform(value:Date): number {

    if(value !==null || value!==undefined){
      const yr=value.getFullYear();
      const mnt=value.getMonth();
      const day=value.getDay();
      const dtNum=parseInt(`${yr}${mnt}${day}`);
      return dtNum;
    }
    return 0;
  }

}
