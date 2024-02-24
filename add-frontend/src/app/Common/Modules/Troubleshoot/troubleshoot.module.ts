import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TroubleshootRoutingModule } from './troubleshoot-routing.module';
import { NotFoundComponent } from './NotFound/not-found.component';
import { NotAvailableComponent } from './NotAvailable/not-available.component';
import { MaterialModule } from '../Material/material.module';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    NotFoundComponent,
    NotAvailableComponent
  ],
  imports: [
    CommonModule,
    TroubleshootRoutingModule,
    MaterialModule,
    RouterModule
  ]
})
export class TroubleshootModule { }
