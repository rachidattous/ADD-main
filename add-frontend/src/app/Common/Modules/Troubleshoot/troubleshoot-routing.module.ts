import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotAvailableComponent } from './NotAvailable/not-available.component';
import { NotFoundComponent } from './NotFound/not-found.component';

const routes: Routes = [
  {path : '404', component : NotFoundComponent},
  {path : '500', component : NotAvailableComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TroubleshootRoutingModule { }
