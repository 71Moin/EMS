import { Component } from '@angular/core';
import { AuthService } from '../../service/Auth/auth.service';
import { Router } from '@angular/router';
import { UserService } from '../../service/user/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
      islogin:boolean=false;
      constructor(
        private autservice:AuthService,
        private router: Router,
        private userService:UserService
        ){
      }
      ngOnInit():void{
        if(localStorage.getItem('islogin')==='Y')
          this.islogin=true;
        else
        this.islogin=false;
      }
      login():void{
        this.logout();
        this.router.navigate(['login']);
      }
      logout():void{
        this.autservice.setAuthenticationStatus(false);
        this.islogin=this.autservice.isLoggedIn();
        this.userService.clearUserName();
        localStorage.clear();
        this.router.navigate(['/login']);
      }
}
