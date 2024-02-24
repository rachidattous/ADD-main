import { ContentState } from "./Enums"


export interface Content {
    
    id : string
    url : string
    ext : string
    uploadDate : string
    originalFileName : string
    fileSize : number
    title ?: string
    description ?: string
    state : ContentState
    validationDate ?: string
    path : string
    fileType : string
    userId : string
    validations : Validation[]
}

export interface Validation {
    id : string
    comment : string
    date : string
    time : string
    userId : string
    validationOrder : number
    validationType : ContentState
}