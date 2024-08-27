import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateStr',
  standalone:true
})
export class DateStrPipe implements PipeTransform {

  transform(val:number): string {
    const value=val.toString();
    if(value.length > 0 && value!=='' && value!==null && value.length===8){
      const year=value.substring(0,4);
      const month=value.substring(4,6);
      const day=value.substring(6);
      return `${day}-${month}-${year}`;
    }
    return '';
  }

}
