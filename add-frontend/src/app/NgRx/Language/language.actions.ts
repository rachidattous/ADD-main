/**
 * Language state
 * action : change language
 */
import { createAction, props } from "@ngrx/store"
import { LanguageState } from "./language.state"


const CHANGE_LANGUAGE = '[LANGUAGE] change'
export const changeLanguage$action = createAction(CHANGE_LANGUAGE, props<LanguageState>())