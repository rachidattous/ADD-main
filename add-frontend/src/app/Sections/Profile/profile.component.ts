import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { Store } from '@ngrx/store';
import { uploadUserPhoto$action } from 'src/app/NgRx/User/user.actions';
import { userImage$selector, userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { ContentService } from 'src/app/Services/Content/content.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  form !: FormGroup
  content : any
  url : any
  userId !: string

  constructor(private fb : FormBuilder, 
    private store : Store<IAppState>, 
    private contentService : ContentService,
    private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      file : ['']
    })
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
    this.store.select(userInfo$selector).subscribe((user : any) => {
      this.userId = user.id
    })
  }

  readURL(){
    const reader = new FileReader()
    reader.onload = e => this.url = reader.result
    reader.readAsDataURL(this.content)
  }

  onProfileSelected(event : any){
    this.content = event.target.files[0]
    this.form.setValue({  ...this.form.value ,file : event.target.files[0]})
    this.readURL()
  }

  Save(){
    this.store.dispatch(uploadUserPhoto$action({userId : this.userId, body : this.form.value}))
  }

}
