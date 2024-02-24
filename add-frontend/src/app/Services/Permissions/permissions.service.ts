import { Injectable } from '@angular/core';
import { Accept, Extension } from 'src/app/Common/Services/consts';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { UtilService } from 'src/app/Common/Services/Util/util.service';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class PermissionsService {

  constructor( private http : HttpClient, private utilService : UtilService ) { }

  loadAll() {
    return this.http.get(`${environment.apiUrl}api/auth/permissions/list`);
  }

  loadPaginate(page : number, size : number){
    return this.http.get(`${environment.apiUrl}api/auth/permissions?page=${page}&size=${size}`);
  }

  update(id : any, data : any) {
    return this.http.patch(`${environment.apiUrl}api/auth/permissions/${id}`, data);
  }

  downloadAll(){
    const params : any = { headers : { accept : Accept.EXCEL_97_2003 }, responseType : 'blob', observe : 'response' }
    return this.http.get(`${environment.apiUrl}api/auth/permissions/export/xls`, params)
  }

  saveFile(data : any){
    let contentDisposition = data.headers.get('content-disposition');
    let fileName = this.utilService.extractFileNameFromHeader(contentDisposition, Extension.EXCEL_97_2003);
    const blob = new Blob([data.body], {type : Accept.EXCEL_97_2003});
    saveAs(blob, fileName);
  }

  upload(file : any){
    const formData = new FormData().append('file', file)
    return this.http.put(`${environment.apiUrl}api/auth/permissions/import/xls`, formData)
  }

}
