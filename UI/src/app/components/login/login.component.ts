import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../service/Auth/auth.service';
import { Router } from '@angular/router';
import { CreateUserComponent } from '../create-user/create-user.component';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from '../../service/user/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  private modalRef!: NgbModalRef;
  spinner:boolean=false;
  forgotPass!:FormGroup
  loginForm={
    username:'',
    password:''
  }
  msg!:string
  isloginerr!:boolean
  constructor(
    private authservice:AuthService,
    private router: Router,
    private dialog: MatDialog,
    private usrService:UserService,
    private snackBar: MatSnackBar,
    private modalService: NgbModal,
    private fb:FormBuilder
    ){
      this.isloginerr=false;
      this.msg="";
    }
  ngOnInit(): void {
    //console.log('loading')
    this.forgotPass=this.fb.group({
      email:['',[Validators.required]],
      otp:['',[Validators.required]],
      password:['',[Validators.required,Validators.minLength(8)]],
      cnfPassword:['',Validators.required]
    },{ validators: this.passwordMatchValidator })
  }
  passwordMatchValidator(group: FormGroup) {
    const password = group.get('password')?.value;
    const cnfPassword = group.get('cnfPassword')?.value;

    return password === cnfPassword ? null : { mismatch: true };
  }
  //forgot password part
  otpMsgForgotPass!:string;
  isSentOtp:boolean=false;
  isValidOtp:boolean=false;
  isValidOtpErr:boolean=false;
  isValidOtpMsg!:string;
  isOtpErr:boolean=false;
  isCnfPassVisible:boolean=false;
  isPassVisible:boolean=false;
  openModal(content: any) {
    this.modalRef = this.modalService.open(content,
       { ariaLabelledBy: 'modal-basic-title' , backdrop: 'static', keyboard: false });
  }
  closeModal() {
    if (this.modalRef) {
      this.modalRef.close();
      location.reload();
    }
  }
  sendOtp():void{
    if(this.forgotPass.get('email')?.valid){
      this.spinner=true;
      this.authservice.forgotOtpSend(this.forgotPass.value).subscribe((res:any)=>{
        if(res && res.status==200){
          this.otpMsgForgotPass=res.msg;
          this.isSentOtp=true;
          this.isOtpErr=false;
        }else{
          this.otpMsgForgotPass=res.msg;
          this.isSentOtp=false;
          this.isOtpErr=true;
        }
        this.spinner=false;
      },(err)=>{
        this.otpMsgForgotPass=err.error.msg;
        this.isSentOtp=false;
        this.isOtpErr=true;
        this.spinner=false;
      })
    }else{
      this.forgotPass.get('email')?.markAsTouched();
    }
  }
  validateOtp():void{
    if(this.forgotPass.get('otp')?.valid){
      this.spinner=true;
      this.authservice.validateForgotOtp(this.forgotPass.value).subscribe((res:any)=>{
        if(res && res.status==200){
          this.isValidOtp=true;
          this.isValidOtpErr=false;
          this.otpMsgForgotPass='';
          this.isValidOtpMsg=res.msg;
        }else{
          this.isValidOtp=false;
          this.isValidOtpErr=true;
          this.isValidOtpMsg=res.msg;
        }
        this.spinner=false;
      },(err)=>{
        this.isValidOtp=false;
        this.isValidOtpErr=true;
        this.isValidOtpMsg=err.error.msg;;
        this.spinner=false;
      })
    }else{
      this.forgotPass.get('otp')?.markAsTouched();
    }
  }
  updatePass():void{
    if(this.forgotPass.valid){
      this.spinner=true;
      this.usrService.updatePassword(this.forgotPass.value).subscribe((res:any)=>{
        if(res && res.status==200){
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
          this.forgotPass.reset();
        }else{
          this.snackBar.open(res.msg, 'Cancel', {
            duration: 5000,
            panelClass: ['snackBarColor'],
          });
        }
        this.spinner=false;
        this.closeModal();
        location.reload();
      },(err)=>{
        this.spinner=false;
        this.closeModal();
        this.snackBar.open(err.error.msg, 'Cancel', {
          duration: 5000,
          panelClass: ['snackBarColor'],
        });
      })
    }else{
      this.forgotPass.markAllAsTouched();
    }
  }
  showPass(passId:string):void{
    if(passId==="pass"){
      this.isPassVisible=!this.isPassVisible;
    }else if(passId==="cnfpass"){
      this.isCnfPassVisible= !this.isCnfPassVisible;
    }
  }
  
  //create user part
  userPopup():void{
    const dialogRef = this.dialog.open(CreateUserComponent, {
      width: '400px',
      height:'400px',
    });
  
    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        console.log('Form submitted:', result);
      }
    });
  }
  UserLogin():void{
    localStorage.clear();
    this.usrService.userLogin(this.loginForm).subscribe((response:any)=>{
      if(response.status==200){
        this.snackBar.open(response.msg, 'Cancel', {
          duration: 5000,
          panelClass: ['snackBarColor'],
        });
        this.isloginerr=false;
        localStorage.setItem('islogin','Y');
        this.authservice.setAuthenticationStatus(true);
        localStorage.setItem('authorization','Bearer '+ response.authorization);
        localStorage.setItem('userName',response.userName);
        this.router.navigate(['employee']);
      }
      else{
        this.msg=response.msg;
        this.isloginerr=true;
        this.snackBar.open(response.msg, 'Cancel', {
          panelClass: ['snackBarColor'],
        });
      }
    },(err)=>{
      console.log(err);
      this.isloginerr=true;
      this.msg=err.error.msg;
      this.snackBar.open(this.msg, 'Cancel', {
        duration: 5000,
        panelClass: ['snackBarColor'],
      });
    })
  }
}
