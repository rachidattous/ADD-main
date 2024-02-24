/**
 * State of sidebar toggle : open | close
 */

export interface SidebarToggleState {
    isOpen : boolean
}

export const initialState : SidebarToggleState = {
    isOpen : true
}