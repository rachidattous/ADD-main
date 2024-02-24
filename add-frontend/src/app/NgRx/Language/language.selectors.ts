import { createFeatureSelector, createSelector } from "@ngrx/store";
import { K_LANGUAGE } from "./language.reducer";
import { LanguageState } from "./language.state";

const language$state = createFeatureSelector<LanguageState>(K_LANGUAGE);

export const language$selector = createSelector(language$state, state => state);