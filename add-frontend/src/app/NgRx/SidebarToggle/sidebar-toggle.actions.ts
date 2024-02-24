/**
 * Sidebar Toggle 
 * action: Open/Close the sidebar
 */

import { createAction } from "@ngrx/store";

const TOGGLE_SIDEBAR = 'toggle_sidebar'
export const toggleSidebar$action = createAction(TOGGLE_SIDEBAR);
