import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingComponent } from './Loading/loading.component';
import { MaterialModule } from '../Modules/Material/material.module';
import { ExpandableListComponent } from './ExpandableList/expandable-list.component';
import { RouterModule } from '@angular/router';
import { NavigationListComponent } from './NavigationList/navigation-list.component';
import { ExpandableNavListComponent } from './ExpandableNavList/expandable-nav-list.component';
import { ToolbarComponent } from './Toolbar/toolbar.component';
import { NotificationMenuComponent } from './NotificationMenu/notification-menu.component';
import { ConfirmationDialogComponent } from './ConfirmationDialog/confirmation-dialog.component';
import { SidebarMenuComponent } from './SidebarMenu/sidebar-menu.component';
import { DocViewerComponent } from './DocViewer/doc-viewer.component';



@NgModule({
  declarations: [
    LoadingComponent,
    ExpandableListComponent,
    NavigationListComponent,
    ExpandableNavListComponent,
    ToolbarComponent,
    NotificationMenuComponent,
    ConfirmationDialogComponent,
    SidebarMenuComponent,
    DocViewerComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
  ],
  exports: [
    LoadingComponent,
    ExpandableListComponent,
    NavigationListComponent,
    ExpandableNavListComponent,
    ToolbarComponent,
    NotificationMenuComponent,
    ConfirmationDialogComponent,
    SidebarMenuComponent
  ]
})
export class ComponentsModule { }
