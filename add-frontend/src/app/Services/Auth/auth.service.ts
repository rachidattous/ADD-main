import { Injectable } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { StorageService } from 'src/app/Common/Services/Storage/storage.service';
import { IUserState } from 'src/app/NgRx/User/user.state';
import {
  getUserPhoto$action,
  login$action,
  logout$action,
  updateUser$action,
} from 'src/app/NgRx/User/user.actions';
import { UsersService } from '../Users/users.service';
import { UserState } from '../Users/User';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Role } from 'src/app/models/Roles/Role';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  // token info
  TOKEN = 'token';
  t_created!: Date;
  t_ttl!: number;

  // ref-token info
  REF_TOKEN = 'ref-token';
  rt_created!: Date;
  rt_ttl!: number;

  constructor(
    private router: Router,
    private store: Store<{ user: IUserState }>,
    private usersService: UsersService,
    private http: HttpClient,
    private storageService: StorageService
  ) {
    this.init();
  }

  init() {
    // init token
    let token = this.storageService.get(this.TOKEN);
    this.t_created = token?.date;
    this.t_ttl = token?.ttl;
    // init ref-token
    let refToken = this.storageService.get(this.REF_TOKEN);
    this.rt_created = refToken?.date;
    this.rt_ttl = refToken?.ttl;
  }

  storeToken(token: any, ttl: number) {
    const data = { value: token, date: new Date(), ttl: ttl };
    this.storageService.store(this.TOKEN, data);
    this.t_created = data.date;
    this.t_ttl = ttl;
  }

  token() {
    return this.storageService.get(this.TOKEN);
  }

  removeToken() {
    this.storageService.remove(this.TOKEN);
  }

  storeRefToken(token: any, ttl: number) {
    const data = { value: token, date: new Date(), ttl: ttl };
    this.storageService.store(this.REF_TOKEN, data);
    this.rt_created = data.date;
    this.rt_ttl = ttl;
  }

  refreshToken() {
    return this.storageService.get(this.REF_TOKEN);
  }

  removeRefToken() {
    this.storageService.remove(this.REF_TOKEN);
  }

  isExpired(created: Date, ttl: number) {
    let helper = new Date(created);
    helper.setSeconds(helper.getSeconds() + ttl);
    return helper <= new Date();
  }

  login(loginForm: any) {
    this.http
      .post(`${environment.apiUrl}api/auth/login`, loginForm.value)
      .subscribe((data: any) => {
        this.storeToken(data.access_token, data.expires_in);
        this.storeRefToken(data.refresh_token, data.refresh_expires_in);
        // user & redirection
        this.usersService.current().subscribe((user: any) => {
          this.store.dispatch(getUserPhoto$action({ userId: user.id }));
          let permissions = user.roles.flatMap(
            (role: Role) => role.permissions
          );
          const navigationExtras: NavigationExtras = {};

          if (user.userStatus === UserState.NEW) {
            this.store.dispatch(
              login$action({ isAuthenticated: false, info: user })
            );
            navigationExtras.state = { newUser: true };
            this.router.navigate(['/auth/change-password'], navigationExtras);
          } else {
            this.store.dispatch(
              login$action({ isAuthenticated: true, info: user })
            );
            this.router.navigate(['/']);
          }
        });
      });
  }

  resetPasswordFirstTime(userId: any, data: any) {
    this.http
      .patch(
        `${environment.apiUrl}api/auth/users/${userId}/resetPasswordFirstTime`,
        data
      )
      .subscribe((user: any) => {
        this.store.dispatch(updateUser$action(user));
        this.router.navigate(['/']);
      });
  }

  logout() {
    const ref = { refreshToken: this.refreshToken()?.value };
    this.http
      .delete(`${environment.apiUrl}api/auth/logout`, { body: ref })
      .subscribe(() => {
        // clear local storage
        this.storageService.clear();
        // change user status
        this.store.dispatch(logout$action());
        // navigate to login
        this.router.navigate(['/auth/login']);
      });
  }

  verifyEmail(code: string): Observable<any> {
    const body = { code };
    return this.http.post(
      `${environment.apiUrl}api/auth/users/verifyEmail`,
      body
    );
  }

  changePassword(
    userId: string,
    passwordData: any,
    isFirstTime: boolean
  ): Observable<any> {
    let url = '';
    if (isFirstTime) {
      url = `${environment.apiUrl}api/auth/users/${userId}/resetPasswordFirstTime`;
      return this.http.patch(url, passwordData);
    } else {
      url = `${environment.apiUrl}api/auth/users/changePassword/${userId}`;
      return this.http.post(url, passwordData);
    }
  }

  forgotPassword(username: string): Observable<any> {
    return this.http.post(
      `${environment.apiUrl}api/auth/users/forgetPassword/${username}`,
      {}
    );
  }
}
