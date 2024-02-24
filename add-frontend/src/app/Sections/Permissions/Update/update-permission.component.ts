import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { PermissionsService } from 'src/app/Services/Permissions/permissions.service';

@Component({
  selector: 'app-update-permission',
  templateUrl: './update-permission.component.html',
  styleUrls: ['./update-permission.component.scss']
})
export class UpdatePermissionComponent implements OnInit {

  permissionForm !: FormGroup 
  
  constructor(@Inject(MAT_DIALOG_DATA) public selected_permission: any,
  private fb : FormBuilder,
  private permissionsService : PermissionsService,
  private alertService : AlertService,
  private dialgRef : MatDialogRef<UpdatePermissionComponent>
  ) { }

  ngOnInit(): void {
    this.permissionForm = this.fb.group({
      permission : [this.selected_permission.permission],
      description : [this.selected_permission.description || ''],
    });
  }

  update(){
    if(this.permissionForm.value.description.length !== 0){
      this.permissionsService.update(this.selected_permission.id, this.permissionForm.value)
      .subscribe(() => {
        this.alertService.succes('Permission Update', 'the permission was updated successfully');
        this.dialgRef.close(true);
      });
    }
  }

}
