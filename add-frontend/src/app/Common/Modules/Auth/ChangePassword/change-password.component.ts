import { Component, OnInit } from '@angular/core';
import {
  AbstractControlOptions,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { AuthService } from 'src/app/Services/Auth/auth.service';
import { User, UserState } from 'src/app/Services/Users/User';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent implements OnInit {
  changePasswordForm!: FormGroup;
  user!: any;
  newUser: boolean = false;
  userStatus: boolean = false;
  isNewUser: boolean = false;
  info$!: Observable<any>;
  constructor(
    private fb: FormBuilder,
    private store: Store<IAppState>,
    private authService: AuthService,
    private alertService: AlertService,
    private router: Router
  ) {
    this.newUser =
      this.router.getCurrentNavigation()?.extras.state?.['newUser'];
  }

  ngOnInit(): void {
    this.store.select(userInfo$selector).subscribe((user: User | null) => {
      this.isNewUser =
        this.newUser || user?.userStatus == UserState.NEW ? true : false;
    });
    this.changePasswordForm = this.fb.group(
      {
        oldPassword: [
          null,
          this.isNewUser ? [] : [Validators.required, Validators.minLength(8)],
        ],
        newPassword: [
          null,
          this.isNewUser ? [] : [Validators.required, Validators.minLength(8)],
        ],
        password: [
          '',
          this.isNewUser ? [Validators.required, Validators.minLength(8)] : [],
        ],
        confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
      },
      {
        validators: [
          this.isNewUser
            ? this.confirmedValidator('password', 'confirmPassword')
            : this.confirmedValidator('newPassword', 'confirmPassword'),
        ],
      } as AbstractControlOptions
    );

    this.store.select(userInfo$selector).subscribe((value: any) => {
      this.user = value;
    });
  }

  check(name: string, error: string): Boolean {
    let controle = this.changePasswordForm.controls[name];
    return controle && controle.touched && controle.hasError(error);
  }

  confirmedValidator(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (
        matchingControl.errors &&
        !matchingControl.errors['confirmedValidator']
      ) {
        return;
      }
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ confirmedValidator: true });
      } else {
        matchingControl.setErrors(null);
      }
    };
  }

  submit() {
    this.authService.resetPasswordFirstTime(
      this.user.id,
      this.changePasswordForm.value
    );
    this.changePasswordForm.reset();
  }

  changePassword() {
    const userId = this.user.id;
    const passwordData = {
      password: this.changePasswordForm.value.password,
      oldPassword: this.changePasswordForm.value.oldPassword,
      newPassword: this.changePasswordForm.value.newPassword,
      confirmPassword: this.changePasswordForm.value.confirmPassword,
    };

    this.authService
      .changePassword(userId, passwordData, this.isNewUser)
      .subscribe({
        next: () => {
          this.alertService.succes(
            'Password change',
            'Password was changed successfully'
          );
          this.router.navigate(['/']);
        },
        error: (error) => {
          console.log(error);
        },
      });
  }
}
