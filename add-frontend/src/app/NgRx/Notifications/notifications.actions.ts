import { createAction, props } from "@ngrx/store"

///////////////////////////////
/// load user's notifs actions
///////////////////////////////

const GET_USER_NOTIFICATIONS = '[NOTIFICATIONS] get all for user'
export const getUserNotifications$action = createAction(GET_USER_NOTIFICATIONS, props<any>())

const GET_USER_NOTIFICATIONS_SUCCEEDED = '[NOTIFICATIONS]  get all for user succeded'
export const getUserNotificationsSucceeded$action = createAction(GET_USER_NOTIFICATIONS_SUCCEEDED, props<any>())

const GET_USER_NOTIFICATIONS_FAILED = '[NOTIFICATIONS] get all for user failed'
export const getUserNotificationsFailed$action = createAction(GET_USER_NOTIFICATIONS_FAILED)

///////////////////////////////
/// load more notifs actions
///////////////////////////////

const GET_USER_MORE_NOTIFICATIONS = '[NOTIFICATIONS] load more for user'
export const getUserMoreNotifications$action = createAction(GET_USER_MORE_NOTIFICATIONS, props<any>())

const GET_USER_MORE_NOTIFICATIONS_SUCCEEDED = '[NOTIFICATIONS] load more for user succeeded'
export const getUserMoreNotificationsSucceeded$action = createAction(GET_USER_MORE_NOTIFICATIONS_SUCCEEDED, props<any>())

const GET_USER_MORE_NOTIFICATIONS_FAILED = '[NOTIFICATIONS] load more for user failed'
export const getUserMoreNotificationsFailed$action = createAction(GET_USER_MORE_NOTIFICATIONS_FAILED)

/////////////////////////////
/// count not-read actions
/////////////////////////////

const GET_NOT_READ_NOTIFICATIONS_COUNT = '[NOTIFICATIONS] get not-read count'
export const getNotReadNotificationsCount$action = createAction(GET_NOT_READ_NOTIFICATIONS_COUNT)

const GET_NOT_READ_NOTIFICATIONS_COUNT_SUCCEEDED = '[NOTIFICATIONS] get not-read count succeded'
export const getNotReadNotificationsCountSucceeded$action = createAction(GET_NOT_READ_NOTIFICATIONS_COUNT_SUCCEEDED, props<any>())

const GET_NOT_READ_NOTIFICATIONS_COUNT_FAILED = '[NOTIFICATIONS] get not-read count failed'
export const getNotReadNotificationsCountFailed$action = createAction(GET_NOT_READ_NOTIFICATIONS_COUNT_FAILED)

/////////////////////////////
/// mark all as read actions
/////////////////////////////

const MARK_ALL_NOTIFICATIONS_AS_READ = '[NOTIFICATIONS] mark all as read'
export const markAllNotificationsAsRead$action = createAction(MARK_ALL_NOTIFICATIONS_AS_READ)

const MARK_ALL_NOTIFICATIONS_AS_READ_SUCCEEDED = '[NOTIFICATIONS] mark all as read succeeded'
export const markAllNotificationsAsReadSucceeded$action = createAction(MARK_ALL_NOTIFICATIONS_AS_READ_SUCCEEDED)

const MARK_ALL_NOTIFICATIONS_AS_READ_FAILED = '[NOTIFICATIONS] mark all as read failed'
export const markAllNotificationsAsReadFailed$action = createAction(MARK_ALL_NOTIFICATIONS_AS_READ_FAILED)

/////////////////////////////
/// mark one as read actions
/////////////////////////////

const MARK_ONE_NOTIFICATIONS_AS_READ = '[NOTIFICATIONS] mark one as read'
export const markOneNotificationsAsRead$action = createAction(MARK_ONE_NOTIFICATIONS_AS_READ, props<any>())

const MARK_ONE_NOTIFICATIONS_AS_READ_SUCCEEDED = '[NOTIFICATIONS] mark one as read succeeded'
export const markOneNotificationsAsReadSucceeded$action = createAction(MARK_ONE_NOTIFICATIONS_AS_READ_SUCCEEDED)

const MARK_ONE_NOTIFICATIONS_AS_READ_FAILED = '[NOTIFICATIONS] mark one as read failed'
export const markOneNotificationsAsReadFailed$action = createAction(MARK_ONE_NOTIFICATIONS_AS_READ_FAILED)

/////////////////////////////
/// web socket notif actions
/////////////////////////////

const LISTEN_ON_NOTIFICATIONS_TOPIC = '[NOTIFICATIONS] listen on topic'
export const listenOnNotificationsTopic$action = createAction(LISTEN_ON_NOTIFICATIONS_TOPIC)

const TRIGGER_RECEIVED_NOTIFICATION = '[NOTIFICATIONS] received notification'
export const triggerReceivedNotification$action = createAction(TRIGGER_RECEIVED_NOTIFICATION, props<any>())

/////////////////////////////
/// refresh loaded notifs
/////////////////////////////

const REFRESH_LOADED_NOTIFICATIONS = '[NOTIFICATIONS] refresh'
export const refreshLoadedNotifications$action = createAction(REFRESH_LOADED_NOTIFICATIONS, props<any>())

const REFRESH_LOADED_NOTIFICATIONS_SUCCEEDED = '[NOTIFICATIONS] refresh succeeded'
export const refreshLoadedNotificationsSucceeded$action = createAction(REFRESH_LOADED_NOTIFICATIONS_SUCCEEDED, props<any>())

const REFRESH_LOADED_NOTIFICATIONS_FAILED = '[NOTIFICATIONS] refresh failed'
export const refreshLoadedNotificationsFailed$action = createAction(REFRESH_LOADED_NOTIFICATIONS_FAILED)