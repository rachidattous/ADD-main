import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { saveAs } from 'file-saver';
import { UtilService } from '../Util/util.service';
import { Extension } from '../consts';
import { AlertService } from '../UI/Alert/alert.service';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http : HttpClient, private utilService : UtilService, private alertService : AlertService) { }

  download(api: string, format : string, extension : string){
    this.http.get(`${environment.apiUrl}${api}`, {
      headers : {
        accept : format
      },
      responseType : 'blob',
      observe : 'response'
    })
    .subscribe((data : any) => {
      let contentDisposition = data.headers.get('content-disposition');
      let fileName = this.utilService.extractFileNameFromHeader(contentDisposition, extension);
      const blob = new Blob([data.body], {type : format});
      saveAs(blob, fileName);
    });
  }

  upload(api : string, file : any){
    const formData = new FormData()
    formData.append('file', file);
    this.http.put(`${environment.apiUrl}${api}`, formData)
    .subscribe(() => {
      this.alertService.info('upload permissions', 'You have to wait for the process to end')
    })
  } 

}
