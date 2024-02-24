/**
 * User state
 * action : login
 * action : logout
 */

import { createAction, props } from "@ngrx/store"
import { IUserState } from "./user.state"
import { User } from "src/app/Services/Users/User"

const LOGIN = 'login'
export const login$action = createAction(LOGIN, props<IUserState>())

const UPDATE_USER = 'update-user'
export const updateUser$action = createAction(UPDATE_USER, props<User>())

const LOGOUT = 'logout'
export const logout$action = createAction(LOGOUT)

////////////////////////////////
/// User Photo
////////////////////////////////

const UPLOAD_USER_PHOTO = '[User] upload'
export const uploadUserPhoto$action = createAction(UPLOAD_USER_PHOTO, props<any>())

const UPLOAD_USER_PHOTO_SUCCEEDED = '[User] upload succeeded'
export const uploadUserPhotoSucceeded$action = createAction(UPLOAD_USER_PHOTO_SUCCEEDED, props<any>())

const UPLOAD_USER_PHOTO_FAILED = '[User] upload failed'
export const uploadUserPhotoFailed$action = createAction(UPLOAD_USER_PHOTO_FAILED)

const GET_USER_PHOTO = '[User] get photo'
export const getUserPhoto$action = createAction(GET_USER_PHOTO, props<any>())

const GET_USER_PHOTO_SUCCEEDED = '[User] get photo succeeded'
export const getUserPhotoSucceeded$action = createAction(GET_USER_PHOTO_SUCCEEDED, props<any>())

const GET_USER_PHOTO_FAILED = '[User] get photo failed'
export const getUserPhotoFailed$action = createAction(GET_USER_PHOTO_FAILED)


////////////////////////////////
/// Connected User Refresh
////////////////////////////////

const REFRESH_USER_DATA = '[User] refresh'
export const refreshUser$action = createAction(REFRESH_USER_DATA)

const REFRESH_USER_DATA_SUCCEEDED = '[User] refresh succeeded'
export const refreshUserSucceeded$action = createAction(REFRESH_USER_DATA_SUCCEEDED, props<any>())

const REFRESH_USER_DATA_FAILED = '[User] refresh failed'
export const refreshUserFailed$action = createAction(REFRESH_USER_DATA_FAILED)