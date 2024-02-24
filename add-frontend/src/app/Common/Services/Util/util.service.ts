import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor() { }

  extractFileNameFromHeader(contentDisposition : string, extension : string){
    
    let pattern = 'filename=(.)*'
    let gPattern = pattern + '\.' + extension;
    let regex = new RegExp(gPattern, 'g');
    let result = contentDisposition.match(regex);
    if(result){
      return result[0].split('=')[1];
    }
    return `file.${extension}`;
  }

}
