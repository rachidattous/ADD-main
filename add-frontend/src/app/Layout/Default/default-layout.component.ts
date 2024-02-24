import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { isOpen$selector } from 'src/app/NgRx/SidebarToggle/sidebar-toggle.selectors';
import { IAppState } from 'src/app/NgRx/reducers';

@Component({
  selector: 'app-default-layout',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss']
})
export class DefaultLayoutComponent implements OnInit {

  open$ : Observable<boolean> = this.store.select(isOpen$selector);

  constructor(private store : Store<IAppState>) { }

  ngOnInit(): void {}

}
