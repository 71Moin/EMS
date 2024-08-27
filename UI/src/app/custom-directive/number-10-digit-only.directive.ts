import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appNumber10DigitOnly]'
})
export class Number10DigitOnlyDirective {
  private regex: RegExp = new RegExp(/^\d{0,10}$/);
  private specialKeys: Array<string> = ['Backspace', 'Tab', 'End', 'Home', 'ArrowLeft', 'ArrowRight', 'Delete'];

  constructor(private el: ElementRef) {}

  @HostListener('keydown', ['$event'])
  onKeyDown(event: KeyboardEvent) {
    if (this.specialKeys.indexOf(event.key) !== -1) {
      return;
    }
    let current: string = this.el.nativeElement.value;
    let next: string = current.concat(event.key);
    if (next && !this.regex.test(next)) {
      event.preventDefault();
    }
  }


}
