import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './Login/login.component';
import { ResetPasswordComponent } from './ResetPassword/reset-password.component';
import { MaterialModule } from '../Material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ChangePasswordComponent } from './ChangePassword/change-password.component';
import { ConfirmationComponent } from './Confirmation/confirmation.component';


@NgModule({
  declarations: [
    LoginComponent,
    ResetPasswordComponent,
    ChangePasswordComponent,
    ConfirmationComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AuthRoutingModule,
    MaterialModule
  ]
})
export class AuthModule { }
