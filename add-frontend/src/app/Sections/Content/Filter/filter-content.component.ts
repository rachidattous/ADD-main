import { formatDate } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FileType } from 'src/app/models/Content/Enums';

@Component({
  selector: 'app-filter-content',
  templateUrl: './filter-content.component.html',
  styleUrls: ['./filter-content.component.scss']
})
export class FilterContentComponent implements OnInit {

  form !: FormGroup

  constructor(
    @Inject(MAT_DIALOG_DATA) public type : FileType,
    private dialgRef : MatDialogRef<FilterContentComponent>, 
    private fb : FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      date : [],
      state : [],
      ext : [],
      fileType : [this.type]
    })
  }

  extensions(){
    switch(this.type){
      case FileType.Document : return ["pdf", "doc", "docx", "xls", "xlsx", "txt"];
      case FileType.Audio : return ["mp3", "voc", "aac", "wma"];
      case FileType.File : return ["zip", "rar"];
      case FileType.Video : return ["mp4", "mov", "wmv", "avi"];
      case FileType.Image : return ["jpg", "jpeg", "png", "gif"];
      default : return []
    }
  }

  filter(){
    if(this.form.value.date){
      this.form.setValue({...this.form.value, date : formatDate(this.form.value.date,'yyyy-MM-dd',"en-US")});
    }
    this.dialgRef.close(this.form.value)
  }

}
