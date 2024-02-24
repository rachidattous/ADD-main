
export interface Notification {
    notification : any
}

export interface NotificationState {
    notifications : any[],
    notOpenedCount : number,
    total : number,
    pages : number
}

export const initialState : NotificationState = {
    notifications : [],
    notOpenedCount : 0,
    total : 0,
    pages : 0
}