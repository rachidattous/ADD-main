import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Login/login.component';
import { ResetPasswordComponent } from './ResetPassword/reset-password.component';
import { ChangePasswordComponent } from './ChangePassword/change-password.component';
import { ConfirmationComponent } from './Confirmation/confirmation.component';
import { AuthGuard } from '../../Guards/Auth/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'change-password',
    component: ChangePasswordComponent,
    canActivate: [AuthGuard],
  },
  { path: 'reset-password', component: ResetPasswordComponent },
  {
    path: 'confirm-email',
    component: ConfirmationComponent,
    data: { code: 'code' },
  },
];

/* 

  {
    path: 'auth/change-password',
    component: ChangePasswordComponent,
    canActivate: [AuthGuard],
  },*/

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule {}
