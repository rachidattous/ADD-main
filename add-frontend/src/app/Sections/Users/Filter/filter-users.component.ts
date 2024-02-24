import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { RolesService } from 'src/app/Services/Roles/roles.service';
import { UsersService } from 'src/app/Services/Users/users.service';
import { Role } from 'src/app/models/Roles/Role';

@Component({
  selector: 'app-filter-users',
  templateUrl: './filter-users.component.html',
  styleUrls: ['./filter-users.component.scss']
})
export class FilterUsersComponent implements OnInit {

  form !: FormGroup
  roles : any = []

  constructor(
    private dialgRef : MatDialogRef<FilterUsersComponent>, 
    private fb : FormBuilder,
    private service : RolesService) 
  {
    this.service.loadAll().subscribe((res : any) => {
      this.roles = res
    })
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      firstName : [],
      lastName : [],
      email : [],
      username : [],
      birthDate : [],
      joinedDate : [],
      gender : [],
      roleName : [],
    })
  }

  filter(){
    if(this.form.value.birthDate){
      this.form.setValue({...this.form.value, birthDate : formatDate(this.form.value.birthDate,'yyyy-MM-dd',"en-US")});
    }
    if(this.form.value.joinedDate){
      this.form.setValue({...this.form.value, joinedDate : formatDate(this.form.value.joinedDate,'yyyy-MM-dd',"en-US")});
    }
    this.dialgRef.close(this.form.value)
  }

}
