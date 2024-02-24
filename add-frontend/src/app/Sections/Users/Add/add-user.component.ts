import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { RolesService } from 'src/app/Services/Roles/roles.service';
import { UsersService } from 'src/app/Services/Users/users.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  userForm !: FormGroup
  roles : any = []

  constructor(private dialgRef : MatDialogRef<AddUserComponent>,
    private fb : FormBuilder,
    private usersService : UsersService,
    private rolesService : RolesService,
    private alertService : AlertService
  ) 
  {
    this.loadRoles();
  }

  ngOnInit(): void {
    this.userForm = this.fb.group({
      firstName : ['', Validators.required],
      lastName : ['', Validators.required],
      email : ['', [Validators.required, Validators.email]],
      birthDate : ['', [Validators.required]],
      username : ['', Validators.required],
      // password : ['', [Validators.required, Validators.minLength(8)]],
      gender : ['', [Validators.required]],
      rolesName : [[], Validators.required]
    });
  }

  loadRoles(){
    this.rolesService.loadAll()
    .subscribe((data : any) => {
        this.roles = data
      });
  }

  add(){
    this.formatDate();
    this.usersService.add(this.userForm.value)
    .subscribe(() => {
        this.alertService.succes('Add User', 'User was added successfully');
        this.dialgRef.close();
      })
  }

  filterAge(date : Date | null) : boolean {
    let today = new Date()
    if( date && date > new Date(today.getFullYear() -18, today.getMonth(), today.getDate()))
      return false;
    return true
  }

  check(name : string, error : string) : Boolean {
    let controle = this.userForm.controls[name];
    return controle && controle.touched && controle.hasError(error);
  }

  formatDate(){
    this.userForm.setValue({ 
      ...this.userForm.value, 
      birthDate : formatDate(this.userForm.value.birthDate,'yyyy-MM-dd',"en-US")
    });
  }

  onRoleRemoved(role : any){
    let clone = this.userForm.value.rolesName
    this.userForm.setValue({
      ...this.userForm.value, 
      rolesName : clone.filter((e : any) => e !== role)
    })
  }

  sliceRoles(){
    return this.userForm.value.rolesName.slice(0,1);
  }

}
