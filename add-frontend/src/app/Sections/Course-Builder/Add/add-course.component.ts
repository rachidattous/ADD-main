import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Store } from '@ngrx/store';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { CourseBuilderService } from 'src/app/Services/Courses-Builder/course-builder.service';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.scss']
})
export class AddCourseComponent implements OnInit {

  currentUserId !: string 
  form !: FormGroup
  image : File | null = null

  constructor(private ref : MatDialogRef<AddCourseComponent>, 
    private fb : FormBuilder, 
    private service : CourseBuilderService,
    private alert : AlertService, 
    private store : Store<IAppState>) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      title : ['', Validators.required],
      summary : ['', Validators.required],
      image : ['', Validators.required],
    })
    this.store.select(userInfo$selector).subscribe((user : any) => {
      this.currentUserId = user.id
    })
  }

  check(name : string, error : string) : Boolean {
    let controle = this.form.controls[name];
    return controle && controle.touched && controle.hasError(error);
  }

  select(event : any){
    this.image =  event.target.files[0]
    this.form.setValue({  ...this.form.value ,image : event.target.files[0]})
  }

  unselect(){
    this.image = null
    this.form.setValue({  ...this.form.value ,image : ''})
  }

  add(){
    this.service.add(this.currentUserId, this.form.value).subscribe(() => {
      this.alert.succes('Add Course', 'Course was added successfully')
      this.ref.close();
    })
  }

}
