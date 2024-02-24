import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/Services/Auth/auth.service';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.scss'],
})
export class ConfirmationComponent implements OnInit {
  loading: boolean = true;
  confirmed: boolean = false;
  code: string | null = null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.code = this.route.snapshot.queryParamMap.get('code');
    if (this.code) {
      this.authService.verifyEmail(this.code).subscribe({
        next: () => {
          this.confirmed = true;
          this.loading = false;
        },
        error: () => {
          this.confirmed = false;
          this.loading = false;
        },
      });
    } else {
      this.router.navigate(['/']);
    }
  }

  navigate() {
    this.router.navigate(['/']);
  }
}
