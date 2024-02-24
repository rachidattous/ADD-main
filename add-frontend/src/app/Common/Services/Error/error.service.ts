import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { HttpClientError, HttpServerError } from '../consts';
import { AlertService } from '../UI/Alert/alert.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(private alertService : AlertService) { }

  handleError(error : any){
    if(error instanceof HttpErrorResponse){
      if(error.error instanceof ErrorEvent){
        // do something ..
      }
      else{
        this.whichStatus(error)
      }
    }
    else{
      // do something ..
    }
    return throwError(() => new Error(error))
  }

  whichStatus(error : any){
    switch(error.status){

      // client side : 
      case HttpClientError.BAD_REQUEST : 
        // handle here
        this.alertService.error('Bad Request', error.error.message)
      break;
      case HttpClientError.UNAUTHORIZED : 
        // handle here
        this.alertService.error('Unauthorized', error.error.message)
      break;
      case HttpClientError.FORBIDDEN : 
        // handle here
      break;
      case HttpClientError.NOT_FOUND : 
        // handle here
        this.alertService.error('Not Found', error.error.message)
      break;
      case HttpClientError.REQUEST_TIMEOUT : 
        // handle here
      break;

      // server side : 
      case HttpServerError.INTERNAL_SERVER_ERROR : 
        // handle here 
        this.alertService.error('Internal Server Error', error.error.message)
      break;
      case HttpServerError.NOT_IMPLEMENTED : 
        // handle here 
      break;
      case HttpServerError.BAD_GATEWAY : 
        // handle here 
      break;
      case HttpServerError.SERVICE_UNAVAILABLE : 
        // handle here 
      break;
      case HttpServerError.GATEWAY_TIMEOUT : 
        // handle here 
      break;
      case HttpServerError.HTTP_VERSION_NOT_SUPPORTED : 
        // handle here 
      break;

      // otherwise
      default : break;
    }
  }

}
