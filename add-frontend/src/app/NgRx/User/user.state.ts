/**
 * State of User
 */

import { User } from "src/app/Services/Users/User"
import { storageService } from "../shared"

export const LS_AUTHENTICATED = 'authenticated'
export const LS_USER_INFO = 'info'
export const LS_USER_PHOTO = 'photo'


export interface IUserState {
    isAuthenticated : boolean,
    info : User | null,
    photo ?: any
}

export const initialState : IUserState = {
    isAuthenticated : storageService.get(LS_AUTHENTICATED) || false,
    info : storageService.get(LS_USER_INFO) || null,
    photo : storageService.get(LS_USER_PHOTO) || null
}

