import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-filter-roles',
  templateUrl: './filter-roles.component.html',
  styleUrls: ['./filter-roles.component.scss']
})
export class FilterRolesComponent implements OnInit {

  form !: FormGroup

  constructor(private dialgRef : MatDialogRef<FilterRolesComponent>, private fb : FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      // TO-DO
    })
  }

  filter(){
    this.dialgRef.close(this.form.value)
  }

}
