import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { AuthService } from 'src/app/Services/Auth/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
})
export class ResetPasswordComponent implements OnInit {
  resetInfoForm!: FormGroup;
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.resetInfoForm = this.fb.group({
      // email: ['', [Validators.required, Validators.email]],
      username: ['', Validators.required],
    });
  }

  // validation
  check(name: string, error: string): Boolean {
    let controle = this.resetInfoForm.controls[name];
    return controle && controle.touched && controle.hasError(error);
  }

  submit() {
    this.authService
      .forgotPassword(this.resetInfoForm.value.username)
      .subscribe({
        next: () => {
          this.alertService.succes(
            'Forgot password',
            'Check your email to reset your password'
          );
          this.router.navigate(['/']);
          this.resetInfoForm.reset();
        },
        error: () => {
          this.alertService.error('Forgot password', 'An error has occured');
        },
      });
  }
}
