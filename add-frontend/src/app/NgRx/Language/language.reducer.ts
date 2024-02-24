import { createReducer, on } from "@ngrx/store";
import { changeLanguage$action } from "./language.actions";
import { initialState } from "./language.state";


export const K_LANGUAGE = 'language';

const _languageReducer = createReducer(
    initialState,
    on(changeLanguage$action, (state, payload) => {
        return {
            ...state,
            icon : payload.icon,
            value : payload.value,
            text : payload.text,
            local : payload.local
        }
    })
)

export function languageReducer(state : any, action : any){
    return _languageReducer(state, action);
}