import { Permission } from "../Permissions/Permission"

export interface Role {
    id ?: string
    name : string
    description : string
    permissions : Permission[]
}