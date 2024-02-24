import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-filter-permissions',
  templateUrl: './filter-permissions.component.html',
  styleUrls: ['./filter-permissions.component.scss']
})
export class FilterPermissionsComponent implements OnInit {

  form !: FormGroup

  constructor(private dialgRef : MatDialogRef<FilterPermissionsComponent>, private fb : FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      // TO-DO
    })
  }

  filter(){
    this.dialgRef.close(this.form.value)
  }

}
