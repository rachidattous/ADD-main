import { createFeatureSelector, createSelector } from "@ngrx/store";
import { NotificationState } from "./notifications.state";
import { K_NOTIFICATIONS } from "./notifications.reducer";


const notifications$state = createFeatureSelector<NotificationState>(K_NOTIFICATIONS);

export const notifications$selector = createSelector(notifications$state, state => state.notifications);

export const notOpenedNotificationsCount$selector = createSelector(notifications$state, state => state.notOpenedCount);

export const totalPagesNotificationsCount$selector = createSelector(notifications$state, state => state.pages);

export const totalNotificationsCount$selector = createSelector(notifications$state, state => state.total);