import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  // store data
  store(key : string, data : any){
    localStorage.setItem(key, JSON.stringify(data))
  }

  // retreive data
  get(key : string) : any {
    let obj = localStorage.getItem(key);
    return  obj ? JSON.parse(obj) : null;
  }

  // remove item from local storage
  remove(key : string){
    localStorage.removeItem(key)
  }

  // clear the local storage
  clear() {
    localStorage.clear()
  }

}
