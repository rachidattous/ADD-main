import { Injectable } from '@angular/core';
import { VIEWS, USER_REF_MENU, USERS_MNG_REF_MENU, PERSONAL_SPACE_REF_MENU, CONTENT_MNG_REF_MENU, COURSE_BUILD_REF_MENU } from 'src/app/Navigation/navigation';

@Injectable({
  providedIn: 'root'
})
export class MenuBuilderService {

  VIEWS = VIEWS;

  constructor() { }

  user(){
    return this.menuBuilder(USER_REF_MENU);
  }

  build(permissions : any){
    let menu : any = []
    menu.push(this.sectionBuilder('Personal Space', false, PERSONAL_SPACE_REF_MENU))
    menu.push(this.sectionBuilder('Users Management', false, USERS_MNG_REF_MENU, permissions))
    menu.push(this.sectionBuilder('Content Management', false, CONTENT_MNG_REF_MENU, permissions))
    menu.push(this.sectionBuilder('Courses Management', false, COURSE_BUILD_REF_MENU, permissions))
    return menu.filter((e : any) => e);
  }

  /////////////////////////////
  /// Private section
  /////////////////////////////

  private sectionBuilder(title : string, hideToggle : boolean, ref : any, permissions ?: any){
      let display = ref;
      if(permissions){
        display = this.intersect(ref, permissions);
        if(!Object.keys(display).length) return ;
      }
      let items : any[] = [];
      for(let i = 0; i < VIEWS.length; i++)
      {
          if(display[VIEWS[i].id])
          {
              items.push(VIEWS[i]);
          }
      }
      return {title, hideToggle, items};
  }

  private menuBuilder(display : any){
      let items : any[] = [];
      for(let i = 0; i < VIEWS.length; i++)
      {
          if(display[VIEWS[i].id])
          {
              items.push(VIEWS[i]);
          }
      }
      return items;
  }

  private intersect(base : any, permissions : any){
    let obj : any = {}
    Object.keys(base).filter(e => permissions[e]).forEach((e) => { obj[e] = e });
    return obj;
  }

}
