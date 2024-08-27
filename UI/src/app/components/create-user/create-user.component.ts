import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../../service/user/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UtilityService } from '../../service/utility.service';
import { AuthService } from '../../service/Auth/auth.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.css'
})
export class CreateUserComponent implements OnInit{
  registerForm!: FormGroup;
  validOtp:boolean=false;
  isgetOtp:boolean=false;
  otpMsg:string="";
  boolOtpMsg:boolean=false;
  spinner:boolean=false;
  openForm:boolean=false;
  rightMail:boolean=false;
  authForm={
    email:'',
    otp:''
  }
  constructor(
    public dialogRef: MatDialogRef<CreateUserComponent>,
    private userService:UserService,
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder,
    private utilityService:UtilityService,
    private authService:AuthService
  ){}
  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      otp:['',[Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      cnfPassword: ['', Validators.required],
    }, { validators: this.passwordMatchValidator });
  }
  passwordMatchValidator(group: FormGroup) {
    const password = group.get('password')?.value;
    const cnfPassword = group.get('cnfPassword')?.value;

    return password === cnfPassword ? null : { mismatch: true };
  }
  userRegister():void{
    const formData=this.utilityService.getFormValuesAsJSON(this.registerForm);
    this.userService.addUser(formData).subscribe((response:any)=>{
      if(response.status==200){
        this.snackBar.open(response.msg, 'Cancel', {
          duration: 5000,
          panelClass: ['snackBarColor'],
        });
        this.cancelPopup();
      }
      else{
        this.snackBar.open(response.msg, 'Cancel', {
          panelClass: ['snackBarColor'],
        });
      }
    },
    (err)=>{
      this.snackBar.open(err.error.msg, 'Cancel', {
        panelClass: ['snackBarColor'],
      });
      console.log(err);
    });
  }
  sendOtp():void{
    const emailValue = this.registerForm.get('username')?.value;
    this.authForm.email=emailValue;
    this.spinner=true;
    if(this.authForm.email !=='' && this.authForm.email!==null){
      this.authService.sendOtp(this.authForm).subscribe((res:any)=>{
        if(res.status==200){
          this.isgetOtp=true;
          this.boolOtpMsg=false;
          this.otpMsg=res.msg;
          this.rightMail=true;
        }else if(res.status==208){
          this.boolOtpMsg=true;
          this.otpMsg=res.msg;
        }
        this.spinner=false;
      },
      (err)=>{
        console.log(err);
        this.boolOtpMsg=true;
        this.otpMsg=err.error.msg;
        this.spinner=false;
      })
    }else{
      this.rightMail=false;
      this.spinner=false;
    }
    
  }
  isvalidotp:boolean=false;
  validotpmsg:string="";
  ValidateOtp():void{
    const otp = this.registerForm.get('otp')?.value;
    this.authForm.otp=otp;
    this.spinner=true;
    this.authService.validateOtp(this.authForm).subscribe((res:any)=>{
      if(res.status==200){
        this.isvalidotp=false;
        this.validOtp=false;
        this.isgetOtp=true;
        this.openForm=true;
        this.validotpmsg=res.msg;
      }
      this.spinner=false;
    },(err)=>{
      this.validOtp=false;
      this.isvalidotp=true;
      this.validotpmsg=err.error.msg;
      this.spinner=false;
    })
  };
  cancelPopup(): void {
    this.dialogRef.close();
  }
}
