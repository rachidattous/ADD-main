import { CourseState } from "./Enums"

export interface Course {
    id : string
    title : string
    summary : string
    state : CourseState
    userId : string
    version : number
    weeks : any[]
    lastModifiedDate : string
    lastModifiedTime : string
    language : string
    imageId : string
    imageData ?: Blob
    createdDate : string
    createdTime : string
    category : string
}
