import { formatDate } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { RolesService } from 'src/app/Services/Roles/roles.service';
import { UsersService } from 'src/app/Services/Users/users.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})
export class UpdateUserComponent implements OnInit {

  userForm !: FormGroup
  roles : any = []

  constructor(@Inject(MAT_DIALOG_DATA) public selected_user: any,
    private dialgRef : MatDialogRef<UpdateUserComponent>,
    private fb : FormBuilder,
    private usersService : UsersService,
    private rolesService : RolesService,
    private alertService : AlertService
  ) 
  {
    this.loadRoles();
  }

  ngOnInit(): void {

    const selected_roles = this.selected_user.roles.map( (r : any) => r.name);
    this.userForm = this.fb.group({
      firstName : [this.selected_user.firstName, Validators.required],
      lastName : [this.selected_user.lastName, Validators.required],
      email : [this.selected_user.email, [Validators.required, Validators.email]],
      birthDate : [this.selected_user.birthDate, Validators.required],
      username : [this.selected_user.username, Validators.required],
      gender : [this.selected_user.gender, Validators.required],
      rolesName : [selected_roles, Validators.required],
    });

  }

  loadRoles(){
    this.rolesService.loadAll()
    .subscribe((data : any) => {
        this.roles = data
      });
  }

  update(){
    this.formatDate();
    this.usersService.update(this.selected_user.id, this.userForm.value)
    .subscribe(() => {
        this.alertService.succes('Update User', 'User was updated successfully');
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
