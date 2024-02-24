import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { TokenInterceptor } from './Common/Services/Interceptors/Token.interceptor';
import { LoadingInterceptor } from './Common/Services/Interceptors/Loading.interceptor';
import { ComponentsModule } from './Common/Components/components.module';
import { ErrorInterceptor } from './Common/Services/Interceptors/Error.interceptor';
import { LayoutModule } from './Layout/layout.module';
import { reducers } from './NgRx/reducers';
import { environment } from 'src/environments/environment';
import { EffectsModule } from '@ngrx/effects';
import { NotificationsEffects } from './NgRx/Notifications/notifications.effects';
import { UserEffects } from './NgRx/User/user.effects';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    ComponentsModule,
    LayoutModule,
    StoreModule.forRoot(reducers),
    StoreDevtoolsModule.instrument({ logOnly : environment.production}),
    EffectsModule.forRoot([NotificationsEffects, UserEffects]),
  ],
  providers: [
              {provide : HTTP_INTERCEPTORS, useClass : TokenInterceptor, multi : true},
              {provide : HTTP_INTERCEPTORS, useClass : LoadingInterceptor, multi : true},
              {provide : HTTP_INTERCEPTORS, useClass : ErrorInterceptor, multi : true},
            ],
  bootstrap: [AppComponent]
})
export class AppModule { }
