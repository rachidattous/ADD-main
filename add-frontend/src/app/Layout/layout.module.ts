import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { AuthLayoutComponent } from './Auth/auth-layout.component';
import { FooterComponent } from './Components/Footer/footer.component';
import { HeaderComponent } from './Components/Header/header.component';
import { MaterialModule } from '../Common/Modules/Material/material.module';
import { DefaultLayoutComponent } from './Default/default-layout.component';
import { SidebarComponent } from './Components/Sidebar/sidebar.component';
import { ComponentsModule } from '../Common/Components/components.module';
import { SectionsModule } from '../Sections/sections.module';
import { TroubleshootLayoutComponent } from './Troubleshoot/troubleshoot-layout.component';

/**
 * Import here all the available layouts
 */

@NgModule({
  declarations: [
    AuthLayoutComponent,
    FooterComponent,
    HeaderComponent,
    DefaultLayoutComponent,
    SidebarComponent,
    TroubleshootLayoutComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    ComponentsModule,
    FormsModule,
    SectionsModule
  ]
})
export class LayoutModule { }
