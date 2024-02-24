import { Injectable } from '@angular/core';
import { AES } from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class EncryptionService {

  secret = 'berexia-add-elearning-app'

  constructor() { }

  encrypt(data : any) {
    return AES.encrypt(JSON.stringify(data), this.secret.trim()).toString();
  }

  decrypt(data : any) {
    const bytes = CryptoJS.AES.decrypt(data, this.secret.trim());
    if (bytes.toString()) {
      return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
    }
    return data;
  }

}
