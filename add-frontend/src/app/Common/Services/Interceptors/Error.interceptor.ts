import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse, HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, switchMap, throwError } from "rxjs";
import { AuthService } from "src/app/Services/Auth/auth.service";
import { AuthenticationEndPoints, HttpClientError } from "../consts";
import { ErrorService } from "../Error/error.service";
import { TokenInterceptor } from "./Token.interceptor";
import { environment } from "src/environments/environment";

/**
 * the service aim to intercept every outgoing http request
 * then handle every occured error
 */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    
    constructor(
        private errorService : ErrorService, 
        private authService : AuthService,
        private http : HttpClient) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        
        return next
                .handle(req)
                .pipe(catchError(error => {
                        if(error instanceof HttpErrorResponse 
                            && !(error.error instanceof ErrorEvent)
                            && error.status === HttpClientError.UNAUTHORIZED)
                        {
                            return this.refreshToken(req, next);
                        }
                        return this.errorService.handleError(error)
                    }))
    }

    refreshToken(req: HttpRequest<any>, next: HttpHandler){
        let refToken =  { refreshToken : this.authService.refreshToken()?.value };
        return this.http.post(`${environment.apiUrl}api/auth/refresh`, refToken)
            .pipe(
                switchMap((data : any) => {
                    this.authService.storeToken(data.access_token, data.expires_in);
                    let token = this.authService.token();
                    req = TokenInterceptor.inject(req, token);
                    return next.handle(req);
                }),
                catchError(error => { 
                    this.authService.logout();
                    return throwError(() => error)
                })
            ) as Observable<HttpEvent<any>>;
    }
    
}