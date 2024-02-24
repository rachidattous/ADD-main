import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSelect } from '@angular/material/select';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { PermissionsService } from 'src/app/Services/Permissions/permissions.service';
import { RolesService } from 'src/app/Services/Roles/roles.service';

@Component({
  selector: 'app-update-role',
  templateUrl: './update-role.component.html',
  styleUrls: ['./update-role.component.scss']
})
export class UpdateRoleComponent implements OnInit {

  roleForm !: FormGroup
  data : any = []
  filtredData : any = [];
  searchControl : FormControl = new FormControl('');

  constructor(@Inject(MAT_DIALOG_DATA) public selected_role: any,
    private dialgRef : MatDialogRef<UpdateRoleComponent>,
    private fb : FormBuilder,
    private rolesService : RolesService,
    private permissionsService : PermissionsService,
    private alertService : AlertService
  ) { 
    this.loadPermissions();
  }

  @ViewChild('multiSelect', { static: true }) multiSelect !: MatSelect;

  ngOnInit(): void {

    const selected_permissions = this.selected_role.permissions.map( (p : any) => p.permission);
    this.roleForm = this.fb.group({
      name : [this.selected_role.name, Validators.required],
      description : [this.selected_role.description],
      permissions : [selected_permissions, Validators.required],
    });

    // listen for search events
    this.searchControl.valueChanges.subscribe(() => {
      this.filter();
    });
  }

  loadPermissions(){
    this.permissionsService.loadAll()
    .subscribe((permissions : any) => {
      this.data = permissions;
      this.filtredData = this.data
    });
  }


  update(){
    this.rolesService.update(this.selected_role.id, this.roleForm.value)
    .subscribe(() => {
        this.alertService.succes('Update Role', 'Role was updated successfully')
        this.dialgRef.close();
      });
  }

  filter(){
    if(this.searchControl.value === ''){
      this.filtredData = this.data;
    }
    else{
      this.filtredData = this.data
        .filter((obj : any) => { 
          return obj?.permission.toLowerCase().includes(this.searchControl.value.toLowerCase())
        });
    }
  }

  onPermissionRemoved(permission : any){
    let clone = this.roleForm.value.permissions
    this.roleForm.setValue({
      ...this.roleForm.value, 
      permissions : clone.filter((e : any) => e !== permission)
    })
  }

  slicePermissions(){
    return this.roleForm.value.permissions.slice(0,2);
  }

}
