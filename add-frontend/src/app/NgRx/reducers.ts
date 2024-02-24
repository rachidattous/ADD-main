import { languageReducer } from "./Language/language.reducer";
import { LanguageState } from "./Language/language.state";
import { notificationsReducer } from "./Notifications/notifications.reducer";
import { NotificationState } from "./Notifications/notifications.state";
import { sidebarToggleReducer } from "./SidebarToggle/sidebar-toggle.reducer";
import { SidebarToggleState } from "./SidebarToggle/sidebar-toggle.state";
import { userReducer } from "./User/user.reducer";
import { IUserState } from "./User/user.state";

export interface IAppState {
    sidebarToggle : SidebarToggleState,
    user : IUserState,
    language : LanguageState,
    notifications : NotificationState,
}

export const reducers = {
    sidebarToggle : sidebarToggleReducer, 
    user : userReducer,
    language : languageReducer,
    notifications : notificationsReducer,
}