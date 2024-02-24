import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSelect } from '@angular/material/select';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { PermissionsService } from 'src/app/Services/Permissions/permissions.service';
import { RolesService } from 'src/app/Services/Roles/roles.service';

@Component({
  selector: 'app-add-role',
  templateUrl: './add-role.component.html',
  styleUrls: ['./add-role.component.scss']
})
export class AddRoleComponent implements OnInit {

  roleForm !: FormGroup
  data : any = []
  filtredData : any = [];
  searchControl : FormControl = new FormControl('');

  constructor(private dialgRef : MatDialogRef<AddRoleComponent>,
    private fb : FormBuilder,
    private rolesService : RolesService,
    private permissionsService : PermissionsService,
    private alertService : AlertService
  ) { 
    this.loadPermissions();
  }

  @ViewChild('multiSelect', { static: true }) multiSelect !: MatSelect;

  ngOnInit(): void {

    this.roleForm = this.fb.group({
      name : ['', Validators.required],
      description : [''],
      permissions : [[], Validators.required],
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

  add(){
    this.rolesService.add(this.roleForm.value)
    .subscribe(() => {
        this.alertService.succes('Add Role', 'Role was added successfully')
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
