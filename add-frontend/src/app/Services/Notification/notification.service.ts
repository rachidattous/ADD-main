import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { environment } from 'src/environments/environment';
import { getNotReadNotificationsCount$action, triggerReceivedNotification$action } from 'src/app/NgRx/Notifications/notifications.actions';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private socket !: any 
  private stompClient !: any
  private currentUserId !: string

  constructor(private store : Store<IAppState>, private http : HttpClient) {
    this.socket = new SockJS(`${environment.apiUrl}api/notifications/stompSocket`)
    this.stompClient = Stomp.over(this.socket)
    this.store.select(userInfo$selector).subscribe((user : any) => {
      this.currentUserId = user?.id
    });
  }

  listenOnPrivateChannel(){
    this.sub(`/api/notifications/topic/notify/user/${this.currentUserId}`, (notification : any) => {
      this.store.dispatch(triggerReceivedNotification$action({notification}))
      this.store.dispatch(getNotReadNotificationsCount$action())
    })
  }

  listUserNotifications(query : any){
    return this.http.get(`${environment.apiUrl}api/notifications/${this.currentUserId}?page=${query.page}&size=${query.size}&sort=${query.sort}`)
  }

  getNotOpenedNotificationsCount(){
    return this.http.get(`${environment.apiUrl}api/notifications/notOpend/${this.currentUserId}`)
  }

  markAllAsRead(){
    return this.http.patch(`${environment.apiUrl}api/notifications/markAllRead/${this.currentUserId}`,{})
  }

  markOneAsRead(id : string){
    return this.http.patch(`${environment.apiUrl}api/notifications/markAsRead/${id}`,{})
  }

  ///////////////////////////
  /// private section
  ///////////////////////////

  private sub( topic : string, callback : any ){
    if(this.stompClient.connected){
      this.stompClient.subscribe(topic, (response : any) : any => {
        callback(JSON.parse(response.body))
      })
    }
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(topic, (response : any) : any => {
        callback(JSON.parse(response.body))
      })
    });
  }
 
}
