import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { NotificationService } from 'src/app/Services/Notification/notification.service';
import { getNotReadNotificationsCount$action, getNotReadNotificationsCountFailed$action, 
        getNotReadNotificationsCountSucceeded$action, 
        getUserMoreNotifications$action, 
        getUserMoreNotificationsFailed$action, 
        getUserMoreNotificationsSucceeded$action, 
        getUserNotifications$action, 
        getUserNotificationsFailed$action, 
        getUserNotificationsSucceeded$action, 
        markAllNotificationsAsRead$action,
        markAllNotificationsAsReadFailed$action,
        markAllNotificationsAsReadSucceeded$action,
        markOneNotificationsAsRead$action,
        markOneNotificationsAsReadFailed$action,
        markOneNotificationsAsReadSucceeded$action,
        refreshLoadedNotifications$action,
        refreshLoadedNotificationsFailed$action,
        refreshLoadedNotificationsSucceeded$action} from './notifications.actions';
import { catchError, exhaustMap, map, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable()
export class NotificationsEffects {

    constructor(private actions$ : Actions, private service : NotificationService){}

    getUserNotifications$ = createEffect(() => 
        this.actions$.pipe(
            ofType(getUserNotifications$action),
            exhaustMap((payload) => this.service.listUserNotifications(payload.query).pipe(
                map((notifications : any) => {
                    return getUserNotificationsSucceeded$action({notifications})
                }),
                catchError(() => of(getUserNotificationsFailed$action()))
            ))
        )
    );

    getUserMoreNotifications$ = createEffect(() => 
        this.actions$.pipe(
            ofType(getUserMoreNotifications$action),
            exhaustMap((payload) => this.service.listUserNotifications(payload.query).pipe(
                map((notifications : any) => {
                    return getUserMoreNotificationsSucceeded$action({notifications})
                }),
                catchError(() => of(getUserMoreNotificationsFailed$action()))
            ))
        )
    );

    getNotReadNotificationsCount$ = createEffect(() => 
        this.actions$.pipe(
            ofType(getNotReadNotificationsCount$action),
            exhaustMap(() => this.service.getNotOpenedNotificationsCount().pipe(
                map((count : any) => getNotReadNotificationsCountSucceeded$action({count})),
                catchError(() => of(getNotReadNotificationsCountFailed$action()))
            ))
        )       
    );

    markAllNotificationsAsRead$ = createEffect(() => 
        this.actions$.pipe(
            ofType(markAllNotificationsAsRead$action),
            exhaustMap(() => this.service.markAllAsRead().pipe(
                map(() => markAllNotificationsAsReadSucceeded$action()),
                catchError(() => of(markAllNotificationsAsReadFailed$action()))
            ))
        )   
    );

    markOneNotificationsAsRead$ = createEffect(() => 
        this.actions$.pipe(
            ofType(markOneNotificationsAsRead$action),
            exhaustMap((payload) => this.service.markOneAsRead(payload.id).pipe(
                map(() => markOneNotificationsAsReadSucceeded$action()),
                catchError(() => of(markOneNotificationsAsReadFailed$action()))
            ))
        )       
    );

    refreshLoadedNotifications$ = createEffect(() => 
        this.actions$.pipe(
            ofType(refreshLoadedNotifications$action),
            exhaustMap((payload) => this.service.listUserNotifications(payload.query).pipe(
                map((notifications : any) => refreshLoadedNotificationsSucceeded$action({notifications})),
                catchError(() => of(refreshLoadedNotificationsFailed$action()))
            ))
        )
    );

}