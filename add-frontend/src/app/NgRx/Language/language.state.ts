/**
 * State of language
 */

const ICON_URL = 'assets'

export interface LanguageState {
    icon : string
    value : string
    text : string
    local : string
}

export const languages : LanguageState[] = [

    { icon : `${ICON_URL}/usa.png`, value : 'en', text : 'English', local : 'en-us'},
    { icon : `${ICON_URL}/fr.png`, value : 'fr', text : 'Frensh', local : 'fr-fr'}, 
    { icon : `${ICON_URL}/mar.png`, value : 'ar', text : 'Arabic', local : 'ar-sa'}
    
]

export const initialState : LanguageState = languages[0]