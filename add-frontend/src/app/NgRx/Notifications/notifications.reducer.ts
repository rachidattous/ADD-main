import { createReducer, on } from "@ngrx/store"
import { initialState } from "./notifications.state"
import { getNotReadNotificationsCountSucceeded$action, 
        getUserMoreNotificationsSucceeded$action, 
        getUserNotificationsSucceeded$action, 
        markAllNotificationsAsReadSucceeded$action, 
        markOneNotificationsAsReadSucceeded$action, 
        refreshLoadedNotificationsSucceeded$action, 
        triggerReceivedNotification$action} from "./notifications.actions";

export const K_NOTIFICATIONS = 'notifications'

const _notificationsReducer = createReducer(
    initialState,
    on(getUserNotificationsSucceeded$action, (state, payload) => {
        return {
            ...state, 
            notifications : payload.notifications.content,
            total : payload.notifications.totalElements,
            pages : payload.notifications.totalPages
        }
    }),
    on(getUserMoreNotificationsSucceeded$action, (state, payload) => {
        let arr = [...state.notifications]
        payload.notifications.content.forEach((element : any) => {
            arr.push(element)
        });
        return {
            ...state, 
            notifications : arr,
            total : payload.notifications.totalElements,
            pages : payload.notifications.totalPages
        }
    }),
    on(refreshLoadedNotificationsSucceeded$action, (state, payload) => {
        return {
            ...state, 
            notifications : payload.notifications.content,
        }
    }),
    on(getNotReadNotificationsCountSucceeded$action, (state, payload) => {
        return {
            ...state,
            notOpenedCount : payload.count
        }
    }),
    on(markAllNotificationsAsReadSucceeded$action, (state) => {
        return {
            ...state,
            notOpenedCount : 0
        }
    }),
    on(markOneNotificationsAsReadSucceeded$action, (state) => {
        return {
            ...state,
            notOpenedCount : state.notOpenedCount === 0 ? state.notOpenedCount : state.notOpenedCount - 1
        }
    }),
    on(triggerReceivedNotification$action, (state, payload) => {
        return {
            ...state,
            notifications : [payload.notification, ...state.notifications]
        }
    })
)

export function notificationsReducer(state : any, action : any){
    return _notificationsReducer(state, action);
}