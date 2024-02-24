import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-courses-filter',
  templateUrl: './courses-filter.component.html',
  styleUrls: ['./courses-filter.component.scss']
})
export class CoursesFilterComponent implements OnInit {

  form !: FormGroup

  constructor(private ref : MatDialogRef<CoursesFilterComponent>, private fb : FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      title : [],
      date : [],
      state : []
    })
  }

  filter(){
    if(this.form.value.date){
      this.form.setValue({...this.form.value, date : formatDate(this.form.value.date,'yyyy-MM-dd',"en-US")});
    }
    this.ref.close(this.form.value)
  }

}
