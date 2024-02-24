import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { getNotReadNotificationsCount$action, getUserMoreNotifications$action, getUserNotifications$action, listenOnNotificationsTopic$action, markAllNotificationsAsRead$action, markOneNotificationsAsRead$action, refreshLoadedNotifications$action } from 'src/app/NgRx/Notifications/notifications.actions';
import { notOpenedNotificationsCount$selector, notifications$selector, totalPagesNotificationsCount$selector } from 'src/app/NgRx/Notifications/notifications.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { NotificationService } from 'src/app/Services/Notification/notification.service';

@Component({
  selector: 'app-notification-menu',
  templateUrl: './notification-menu.component.html',
  styleUrls: ['./notification-menu.component.scss']
})
export class NotificationMenuComponent implements OnInit {

  visible : boolean = false;
  query = { page : 0, size : 5, sort : ["date", "desc"]}
  allow : boolean = true;

  notifications$ : Observable<any> = this.store.select(notifications$selector)
  notOpenedCount$ : Observable<number> = this.store.select(notOpenedNotificationsCount$selector)
  totalPages$ : Observable<number> = this.store.select(totalPagesNotificationsCount$selector)

  constructor( private store : Store<IAppState>, private service : NotificationService ) { }

  ngOnInit(): void {
    this.service.listenOnPrivateChannel()
    this.store.dispatch(getUserNotifications$action({ query : this.query }))
    this.store.dispatch(getNotReadNotificationsCount$action())
  }

  markAllAsRead(){
    this.store.dispatch(markAllNotificationsAsRead$action())
    setTimeout( () => 
      this.store.dispatch(refreshLoadedNotifications$action({ query : {
        page : 0,
        size : (this.query.page+1) * this.query.size,
        sort : ["date", "desc"]
      }})),
      2000
    )
    
  }

  markAsRead(id : string){
    this.store.dispatch(markOneNotificationsAsRead$action({id : id}))
    setTimeout( () => 
      this.store.dispatch(refreshLoadedNotifications$action({ query : {
        page : 0,
        size : (this.query.page+1) * this.query.size,
        sort : ["date", "desc"]
      }})),
      2000
    )
  }

  loadMore(){
    this.totalPages$.subscribe((value : number) => {
      if(value <= this.query.page + 1) this.allow = false
    })
    if(!this.allow) return;
    this.query = { ...this.query, page : this.query.page + 1}
    this.store.dispatch(getUserMoreNotifications$action({query : this.query}))
  }

  SentXAgo(date : string){
    let d = new Date()
    let d2 = new Date(date)
    let elapsed = Math.trunc((d.getTime() - d2.getTime())/(1000*60));
    if(elapsed < 60) {
      return "" + elapsed + " minutes ago"
    }
    elapsed /= 60
    if(elapsed < 24) {
      return "" + elapsed + "hours ago"
    }
    let splited = date.split('T')
    let time = splited[1].split(':')
    return splited[0] + ' ' + time[0]+ ':' + time[1];
  }

  notifBgStyle(state : string){
    return state === 'Opened' ? 'notif-read' : 'notif-not-read'
  }

}
