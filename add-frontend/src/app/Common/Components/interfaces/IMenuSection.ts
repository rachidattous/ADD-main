import { IMenuItem } from "./IMenuItem";

export interface IMenuSection {
    title : string;
    hideToggle : boolean;
    items : Array<IMenuItem>;
}