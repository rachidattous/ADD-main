import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { AuthService } from "src/app/Services/Auth/auth.service";
import { AuthenticationEndPoints } from "../consts";
import { StorageService } from "../Storage/storage.service";

/**
 * the service aim to intercept every outgoing http request
 * then inject the authorization token if it exists
 */
@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    
    constructor(private authService : AuthService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if(this.canInject(req))
        {
            let token = this.authService.token()
            req = TokenInterceptor.inject(req, token);
        }
        return next.handle(req)
    }

    canInject(req: HttpRequest<any>){
        return !req.url.includes(AuthenticationEndPoints.Logout) && 
        !req.url.includes(AuthenticationEndPoints.Refresh) && 
        !req.url.includes(AuthenticationEndPoints.Login) &&
        !req.url.includes('api/auth/test/hi')
    }

    static inject(req: HttpRequest<any>, token : any){
        let headers_config : any = {}
        if(!!token) {
            headers_config['setHeaders'] =  {'Authorization': `Bearer ${token.value}`}
        }
        req = req.clone(headers_config)
        return req;
    }

    
    
}