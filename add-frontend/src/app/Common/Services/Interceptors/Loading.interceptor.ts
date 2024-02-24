import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { finalize, Observable } from "rxjs";
import { LoadingService } from "../UI/Loading/loading.service";

/**
 * the service aim to intercept every outgoing http request
 * then show a global loader and hide it after completing 
 * the task
 */
@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
    
    constructor(private loadingService : LoadingService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.loadingService.show()
        return next.handle(req).pipe(finalize(() => this.loadingService.hide()))
    }
    
}