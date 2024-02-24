import { Role } from "../Roles/Role"
import { UserState } from "./Enums"

export interface User {
    id ?: string
    firstName : string
    lastName : string
    username : string
    emailVerified : boolean
    roles : Role[], 
    userStatus : UserState
}