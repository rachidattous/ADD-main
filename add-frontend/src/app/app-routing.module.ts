import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthLayoutComponent } from './Layout/Auth/auth-layout.component';
import { DefaultLayoutComponent } from './Layout/Default/default-layout.component';
import { TroubleshootLayoutComponent } from './Layout/Troubleshoot/troubleshoot-layout.component';
import { AuthGuard } from './Common/Guards/Auth/auth.guard';
const routes: Routes = [
  {
    path: 'auth',
    component: AuthLayoutComponent,
    loadChildren: () =>
      import('./Common/Modules/Auth/auth.module').then((e) => e.AuthModule),
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    canActivate: [AuthGuard],
    loadChildren: () =>
      import('./Sections/sections.module').then((e) => e.SectionsModule),
  },
  {
    path: 'error',
    component: TroubleshootLayoutComponent,
    loadChildren: () =>
      import('./Common/Modules/Troubleshoot/troubleshoot.module').then(
        (e) => e.TroubleshootModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
