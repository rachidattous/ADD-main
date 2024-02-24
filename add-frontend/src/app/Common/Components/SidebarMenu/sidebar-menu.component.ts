import { Component, OnInit } from '@angular/core';
import { MenuBuilderService } from '../../Services/UI/MenuBuilder/menu-builder.service';
import { Store } from '@ngrx/store';
import { IAppState } from 'src/app/NgRx/reducers';
import { userRoles$selector } from 'src/app/NgRx/User/user.selectors';

@Component({
  selector: 'app-sidebar-menu',
  templateUrl: './sidebar-menu.component.html',
  styleUrls: ['./sidebar-menu.component.scss']
})
export class SidebarMenuComponent implements OnInit {

  permissions !: any
  menu : any = [];

  constructor(private mb : MenuBuilderService, private store : Store<IAppState>) { }

  ngOnInit(): void {
    this.store.select(userRoles$selector).subscribe((roles : any) => {
      this.permissions = this.extractPermissions(roles);
      this.menu = this.mb.build(this.permissions);
    });
  }

  private extractPermissions(roles : any){
    let obj : any = {}
    roles?.flatMap((role : any) => role.permissions)
                .forEach((p : any) => {
                  obj[p.permission] = p.permission
                });
    return obj;
  }

}
