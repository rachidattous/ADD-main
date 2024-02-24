import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/Services/Auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  // reset password url
  RESET_PASSWORD = '../reset-password'
  // login form group
  loginForm !: FormGroup 
  // password visibility
  isVisible : Boolean = false
  fieldType : string = 'password'

  constructor(
    private fb : FormBuilder,
    private authService : AuthService) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username : ['', Validators.required],
      password : ['', Validators.required]
    });
  }

  // validation
  check(name : string, error : string) : Boolean {
    let controle = this.loginForm.controls[name];
    return controle && controle.touched && controle.hasError(error);
  }

  passwordIcon(){
    return this.isVisible ? 'visibility' : 'visibility_off'
  }

  toggleVisibility(){
    this.isVisible = !this.isVisible
    this.fieldType = this.isVisible ? 'text' : 'password'
  }

  submit(){
    this.authService.login(this.loginForm);
  }

}
