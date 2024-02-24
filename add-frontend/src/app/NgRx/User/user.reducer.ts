import { createReducer, on } from "@ngrx/store";
import { storageService } from "../shared";
import { initialState, IUserState, LS_AUTHENTICATED, LS_USER_INFO, LS_USER_PHOTO } from "./user.state";
import { getUserPhotoSucceeded$action, login$action, logout$action, refreshUserSucceeded$action, updateUser$action, uploadUserPhotoSucceeded$action } from "./user.actions";

export const K_USER = 'user';

const _userReducer = createReducer(
    initialState,
    on(login$action, (state, payload) => {
        storageService.store(LS_AUTHENTICATED, true);
        storageService.store(LS_USER_INFO, payload.info);
        return {
            ...state,
            isAuthenticated : true,
            info : payload.info,
        }
    }),
    on(refreshUserSucceeded$action, (state, payload) => {
        return {
            ...state,
            info : payload.user
        }
    }),
    on(getUserPhotoSucceeded$action, uploadUserPhotoSucceeded$action, (state, payload) => {
        storageService.store(LS_USER_PHOTO, payload.photo)
        return {
            ...state,
            photo : payload.photo
        }
    }),
    on(updateUser$action, (state, payload) => {
        return {
            ...state,
            info : payload
        }
    }),
    on(logout$action, (state) => {
        storageService.store(LS_AUTHENTICATED, false); // to be modified
        storageService.remove(LS_USER_INFO);
        return {
            ...state,
            isAuthenticated : false,
            info : null,
            permissions : []
        }
    }),
)

export function userReducer(state : any, action : any){
    return _userReducer(state, action);
}