
export enum Display {
    SHOW_PROFILE = 'SHOW_PROFILE',
    SHOW_SETTINGS = 'SHOW_SETTINGS',
    SHOW_HOME = 'SHOW_HOME',
    SHOW_CALENDAR = 'SHOW_CALENDAR',
    SHOW_USERS = 'SHOW_USERS',
    SHOW_ROLES = 'SHOW_ROLES',
    SHOW_PERMISSIONS = 'SHOW_PERMISSIONS',
    SHOW_CONTENT_VIDEOS = 'SHOW_CONTENT_VIDEOS',
    SHOW_CONTENT_DOCUMENTS = 'SHOW_CONTENT_DOCUMENTS',
    SHOW_CONTENT_IMAGES = 'SHOW_CONTENT_IMAGES',
    SHOW_CONTENT_AUDIOS = 'SHOW_CONTENT_AUDIOS',
    SHOW_CONTENT_FILES = 'SHOW_CONTENT_FILES',
    SHOW_COURSES_BUILDER_LIST = 'SHOW_COURSES_BUILDER_LIST',
    SHOW_EVALUATE_CONTENT = 'SHOW_EVALUATE_CONTENT',
    SHOW_REPLACE_CONTENT = 'SHOW_REPLACE_CONTENT'
}

export const VIEWS = [
    {
        id : Display.SHOW_PROFILE,
        text : 'Profile',
        link : '/profile',
        icon : 'person'
    },
    {
        id : Display.SHOW_SETTINGS,
        text : 'Settings',
        link : '/settings',
        icon : 'settings'
    },
    {
        id : Display.SHOW_HOME,
        text : 'Home',
        link : '/home',
        icon : 'home'
    },
    {
        id : Display.SHOW_CALENDAR,
        text : 'Calendar',
        link : '/calendar',
        icon : 'calendar_today'
    },
    {
        id : Display.SHOW_USERS,
        text : 'Users',
        link : '/users',
        icon : 'people'
    },
    {
        id : Display.SHOW_ROLES,
        text : 'Roles',
        link : '/roles',
        icon : 'fingerprint'
    },
    {
        id : Display.SHOW_PERMISSIONS,
        text : 'Permissions',
        link : '/permissions',
        icon : 'lock'
    },
    {
        id : Display.SHOW_CONTENT_DOCUMENTS,
        text : 'Documents',
        link : '/content/documents',
        icon : 'file_copy'
    },  
    {
        id : Display.SHOW_CONTENT_IMAGES,
        text : 'Images',
        link : '/content/images',
        icon : 'image'
    },
    {
        id : Display.SHOW_CONTENT_VIDEOS,
        text : 'Videos',
        link : '/content/videos',
        icon : 'video_library'
    },
    {
        id : Display.SHOW_CONTENT_AUDIOS,
        text : 'Audios',
        link : '/content/audios',
        icon : 'audiotrack'
    },
    {
        id : Display.SHOW_CONTENT_FILES,
        text : 'Files',
        link : '/content/files',
        icon : 'folder'
    },
    {
        id : Display.SHOW_COURSES_BUILDER_LIST,
        text : 'Courses',
        link : '/builder/courses/',
        icon : 'build'
    },
]

////////////////////////
/// user header menu
////////////////////////
export const USER_REF_MENU = {
    SHOW_PROFILE : Display.SHOW_PROFILE,
    SHOW_SETTINGS : Display.SHOW_SETTINGS
}

////////////////////////
/// personal space menu
////////////////////////
export const PERSONAL_SPACE_REF_MENU = {
    SHOW_HOME : Display.SHOW_HOME,
    SHOW_CALENDAR : Display.SHOW_CALENDAR
}

/////////////////////////
/// users managemnt menu
/////////////////////////
export const USERS_MNG_REF_MENU = {
    SHOW_USERS : Display.SHOW_USERS,
    SHOW_ROLES : Display.SHOW_ROLES,
    SHOW_PERMISSIONS : Display.SHOW_PERMISSIONS
}


///////////////////////////
/// content managemnt menu
///////////////////////////
export const CONTENT_MNG_REF_MENU = {
    SHOW_CONTENT_VIDEOS : 'SHOW_CONTENT_VIDEOS',
    SHOW_CONTENT_DOCUMENTS : 'SHOW_CONTENT_DOCUMENTS',
    SHOW_CONTENT_IMAGES : 'SHOW_CONTENT_IMAGES',
    SHOW_CONTENT_AUDIOS : 'SHOW_CONTENT_AUDIOS',
    SHOW_CONTENT_FILES : 'SHOW_CONTENT_FILES'
}


///////////////////////////
/// course builder menu
///////////////////////////
export const COURSE_BUILD_REF_MENU = {
    SHOW_COURSES_BUILDER_LIST : 'SHOW_COURSES_BUILDER_LIST'
}
