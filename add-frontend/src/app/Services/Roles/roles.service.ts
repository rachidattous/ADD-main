import { Injectable } from '@angular/core';
import { Accept, Extension } from 'src/app/Common/Services/consts';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import * as saveAs from 'file-saver';
import { UtilService } from 'src/app/Common/Services/Util/util.service';

@Injectable({
  providedIn: 'root'
})
export class RolesService {

  constructor(
    private http : HttpClient,
    private utilService : UtilService  
  ) { }

  loadAll() {
    return this.http.get(`${environment.apiUrl}api/auth/roles/list`);
  }

  loadPaginate(page : number, size : number){
    return this.http.get(`${environment.apiUrl}api/auth/roles?page=${page}&size=${size}`);
  }

  update(id : any, data : any) {
    return this.http.patch(`${environment.apiUrl}api/auth/roles/${id}`, data);
  }

  add(data : any) {
    return this.http.post(`${environment.apiUrl}api/auth/roles`, data);
  }

  delete(id : any , data ?: any) {
    return this.http.delete(`${environment.apiUrl}api/auth/roles/${id}`, {body : data});
  }

  downloadAll(){
    const params : any = { headers : { accept : Accept.EXCEL_97_2003 }, responseType : 'blob', observe : 'response' }
    return this.http.get(`${environment.apiUrl}api/auth/roles/export/xls`, params)
  }

  saveFile(data : any){
    let contentDisposition = data.headers.get('content-disposition');
    let fileName = this.utilService.extractFileNameFromHeader(contentDisposition, Extension.EXCEL_97_2003);
    const blob = new Blob([data.body], {type : Accept.EXCEL_97_2003});
    saveAs(blob, fileName);
  }

  upload(file : any){
    const formData = new FormData().append('file', file)
    return this.http.put(`${environment.apiUrl}api/auth/roles/import/xls`, formData)
  }

}
