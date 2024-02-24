import { createReducer, on } from "@ngrx/store";
import { toggleSidebar$action } from "./sidebar-toggle.actions";
import { initialState } from "./sidebar-toggle.state";


export const K_SIDEBAR_TOGGLE = 'sidebarToggle';

const _sidebarToggleReducer = createReducer(
    initialState, 
    on(toggleSidebar$action , (state) => { return { ...state, isOpen: !state.isOpen }}),
);

export function sidebarToggleReducer(state: any, action: any){
    return _sidebarToggleReducer(state, action);
}