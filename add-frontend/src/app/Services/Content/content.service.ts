import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import * as saveAs from 'file-saver';
import { Observable } from 'rxjs';
import { UtilService } from 'src/app/Common/Services/Util/util.service';
import { userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import {
  AddContentRequest,
  ContentEvaluationRequest,
  ContentSearchByIdsRequest,
  ContentSuggestionRequest,
  FileSearchRequest,
  ReplaceContentRequest,
} from 'src/app/models/Content/Requests';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ContentService {
  private currentUserId!: string | undefined;

  constructor(
    private http: HttpClient,
    private store: Store<IAppState>,
    private utilService: UtilService
  ) {
    this.store.select(userInfo$selector).subscribe((user) => {
      this.currentUserId = user?.id;
    });
  }

  add(query: AddContentRequest) {
    const formData = new FormData();
    formData.append('file', query.file);
    formData.append('title', query.title);
    formData.append('description', query.description);
    formData.append('userId', query.userId);
    return this.http.put(`${environment.apiUrl}api/file/contents`, formData);
  }

  loadPaginate(
    page: number,
    size: number,
    body: FileSearchRequest
  ): Observable<any> {
    return this.http.post<any>(
      `${environment.apiUrl}api/file/contents/search?page=${page}&size=${size}`,
      body
    );
  }

  loadByIds(page: number, size: number, body: ContentSearchByIdsRequest) {
    return this.http.post(
      `${environment.apiUrl}api/file/contents/ids?page=${page}&size=${size}`,
      body
    );
  }

  delete(id: string) {
    return this.http.delete(`${environment.apiUrl}api/file/${id}`);
  }

  download(id: string) {
    const params: any = { responseType: 'blob', observe: 'response' };
    return this.http.get(
      `${environment.apiUrl}api/file/download/${id}`,
      params
    );
  }

  getFile(id: string) {
    return this.http.get(`${environment.apiUrl}api/file/${id}`);
  }

  saveFile(data: any, extension: string) {
    let contentDisposition = data.headers.get('content-disposition');
    let fileName = this.utilService.extractFileNameFromHeader(
      contentDisposition,
      extension
    );
    const blob = new Blob([data.body]);
    saveAs(blob, fileName);
  }

  stream(id: string) {
    const params: any = { responseType: 'blob', observe: 'response' };
    return this.http.get(`${environment.apiUrl}api/file/stream/${id}`, params);
  }

  loadSuggestions(page: number, size: number, body: ContentSuggestionRequest) {
    return this.http.post(
      `${environment.apiUrl}api/search/fileContent/suggestions?page=${page}&size=${size}`,
      body
    );
  }

  evaluateContent(id: string, body: ContentEvaluationRequest) {
    return this.http.post(
      `${environment.apiUrl}api/file/contents/validate/${id}`,
      body
    );
  }

  replaceContent(id: string, body: ReplaceContentRequest) {
    const formData = new FormData();
    formData.append('file', body.file);
    formData.append('title', body.title);
    formData.append('description', body.description);
    formData.append('userId', 'test');
    return this.http.put(
      `${environment.apiUrl}api/file/contents/replace/${id}`,
      formData
    );
  }

  getUserPhoto(userId: string) {
    return this.http.get(`${environment.apiUrl}api/file/userPhoto/${userId}`);
  }

  uploadUserPhoto(userId: string, query: any) {
    const formData = new FormData();
    formData.append('file', query.file);
    return this.http.put(
      `${environment.apiUrl}api/file/userPhoto/${userId}`,
      formData
    );
  }
}
