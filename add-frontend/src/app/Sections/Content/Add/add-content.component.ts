import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Store } from '@ngrx/store';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { ContentService } from 'src/app/Services/Content/content.service';
import { User } from 'src/app/Services/Users/User';
import { FileType } from 'src/app/models/Content/Enums';

@Component({
  selector: 'app-add-content',
  templateUrl: './add-content.component.html',
  styleUrls: ['./add-content.component.scss']
})
export class AddContentComponent implements OnInit {

  form !: FormGroup
  content : File | null = null 
  currentUser !: User | null;

  constructor(@Inject(MAT_DIALOG_DATA) public type : FileType,
    private dialogRef : MatDialogRef<AddContentComponent>, 
    private fb : FormBuilder, 
    private alertService : AlertService,
    private service : ContentService,
    private store : Store<IAppState>, ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      title : ['', Validators.required],
      description : ['', Validators.required],
      file : ['', Validators.required],
    })
    this.store.select(userInfo$selector).subscribe((user : User | null) => {
      this.currentUser = user;
    })
  }

  onFileUploadSelected(event : any){
    this.content =  event.target.files[0]
    this.form.setValue({  ...this.form.value ,file : event.target.files[0]})
  }

  onFileUploadUnselect(){
    this.content = null
    this.form.setValue({  ...this.form.value ,file : ''})
  }

  add(){    

    this.service.add({ userId : this.currentUser?.id, ...this.form.value})
    .subscribe(() => {
      this.alertService.succes('Upload content', 'Content was uploaded successfully')
      this.dialogRef.close()
    })
  }

  check(name : string, error : string) : Boolean {
    let controle = this.form.controls[name];
    return controle && controle.touched && controle.hasError(error);
  }

  accept(){
    switch(this.type){
      case FileType.Image : return 'image/*'
      case FileType.Video : return 'video/*'
      case FileType.Document : return 'application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, text/plain, application/pdf'
      case FileType.Audio : return 'audio/*'
      default : return '*/*'
    }
  }

}
