import { Component, OnInit } from '@angular/core';

import { Store } from '@ngrx/store';
import { IAppState } from 'src/app/NgRx/reducers';
import { Observable } from 'rxjs';
import { userImage$selector, userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { ContentService } from 'src/app/Services/Content/content.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  info$ !: Observable<any>;
  url !: any

  constructor(private store : Store<IAppState>, 
    private contentService : ContentService,
    private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.info$ = this.store.select(userInfo$selector);
    this.store.select(userImage$selector).subscribe((image : any) => {
      if(!image){
        this.url = "assets/user.png"
      } else {
        this.contentService.stream(image.id).subscribe((data : any) => {
          let objectURL = URL.createObjectURL(data.body);
          this.url = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        })
      }
    })
  }



}
