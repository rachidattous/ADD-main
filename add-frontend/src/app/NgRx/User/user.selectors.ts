import { createFeatureSelector, createSelector } from "@ngrx/store";
import { K_USER } from "./user.reducer";
import { IUserState } from "./user.state";


const user$state = createFeatureSelector<IUserState>(K_USER);

export const isAuthenticated$selector = createSelector(user$state, state => state.isAuthenticated);
export const userInfo$selector = createSelector(user$state, state => state.info);
export const userRoles$selector = createSelector(user$state, state => state.info?.roles)
export const userImage$selector = createSelector(user$state, state => state.photo); 