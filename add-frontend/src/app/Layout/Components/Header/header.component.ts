import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { toggleSidebar$action } from 'src/app/NgRx/SidebarToggle/sidebar-toggle.actions';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/Services/Auth/auth.service';
import { LanguageState } from 'src/app/NgRx/Language/language.state';
import { changeLanguage$action } from 'src/app/NgRx/Language/language.actions';
import { languages } from 'src/app/NgRx/Language/language.state'; 
import { language$selector } from 'src/app/NgRx/Language/language.selectors';
import { isAuthenticated$selector } from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { MenuBuilderService } from 'src/app/Common/Services/UI/MenuBuilder/menu-builder.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  languages !: LanguageState[]
  userMoreMenu : any

  language$ : Observable<LanguageState> = this.store.select(language$selector);
  isAuthenticated$ : Observable<boolean> = this.store.select(isAuthenticated$selector);

  constructor(
    private router : Router,
    private store : Store<IAppState>,
    private authService : AuthService,
    private mb : MenuBuilderService) 
  { }

  ngOnInit(): void {
    this.languages = languages
    this.userMoreMenu = this.mb.user();
  }

  selectLanguage(language : LanguageState){
    this.store.dispatch(changeLanguage$action(language))
  }

  toggle(){
    this.store.dispatch(toggleSidebar$action())
  }

  navigate(link : any){
    this.router.navigate([link])
  }

  logout(){
    this.authService.logout();
  }

}
