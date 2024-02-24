import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Store } from '@ngrx/store';
import { isAuthenticated$selector } from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  private isAuthenticated !: boolean

  constructor(private store : Store<IAppState>, private router : Router){
    this.store.select(isAuthenticated$selector)
    .subscribe((value) => {
      this.isAuthenticated = value;
    })
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {
    if(!this.isAuthenticated){
      this.router.navigate(['/auth/login'])
    }
    return this.isAuthenticated;
    // return true;
  }
  
}
