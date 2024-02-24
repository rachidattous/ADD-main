import { createFeatureSelector, createSelector } from "@ngrx/store";
import { K_SIDEBAR_TOGGLE } from "./sidebar-toggle.reducer";
import { SidebarToggleState } from "./sidebar-toggle.state";


const sidebarToggle$state = createFeatureSelector<SidebarToggleState>(K_SIDEBAR_TOGGLE);

export const isOpen$selector = createSelector(sidebarToggle$state, state => state.isOpen);