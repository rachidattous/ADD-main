import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { ContentService } from 'src/app/Services/Content/content.service';
import { Content } from 'src/app/models/Content/Content';
import { FileType } from 'src/app/models/Content/Enums';

@Component({
  selector: 'app-content-replace',
  templateUrl: './content-replace.component.html',
  styleUrls: ['./content-replace.component.scss']
})
export class ContentReplaceComponent implements OnInit {

  form !: FormGroup
  file : File | null = null

  constructor(
    @Inject(MAT_DIALOG_DATA) public content : Content,
    private dialogRef : MatDialogRef<ContentReplaceComponent>, 
    private fb : FormBuilder, 
    private alertService : AlertService,
    private service : ContentService
  ) { }

  ngOnInit(): void {
    this.file = new File([], this.content.originalFileName);
    this.form = this.fb.group({
      title : [this.content.title, Validators.required],
      description : [this.content.description, Validators.required],
      file : ['', Validators.required],
    })

  }

  onFileUploadSelected(event : any){
    this.file =  event.target.files[0]
    this.form.setValue({  ...this.form.value ,file : event.target.files[0]})
  }

  onFileUploadUnselect(){
    this.file = null
    this.form.setValue({  ...this.form.value ,file : ''})
  }

  replace(){
    this.service.replaceContent(this.content.id, this.form.value)
    .subscribe(() => {
      this.alertService.info('Replace content', 'Content was replaced successfully')
      this.dialogRef.close()
    })
  }

  check(name : string, error : string) : Boolean {
    let controle = this.form.controls[name];
    return controle && controle.touched && controle.hasError(error);
  }

  accept(){
    switch(this.content.fileType){
      case FileType.Image : return 'image/*'
      case FileType.Video : return 'video/*'
      case FileType.Document : return 'application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, text/plain, application/pdf'
      case FileType.Audio : return 'audio/*'
      default : return '*/*'
    }
  }

}
