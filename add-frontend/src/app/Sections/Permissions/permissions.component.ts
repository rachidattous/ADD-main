import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { UpdatePermissionComponent } from './Update/update-permission.component';
import { Permission } from 'src/app/Services/Permissions/Permission';
import { PermissionsService } from 'src/app/Services/Permissions/permissions.service';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { FilterPermissionsComponent } from './Filter/filter-permissions.component';

@Component({
  selector: 'app-permissions',
  templateUrl: './permissions.component.html',
  styleUrls: ['./permissions.component.scss'],
})
export class PermissionsComponent implements OnInit {
  columns: string[] = ['permission', 'description', 'actions'];
  permissions: any = [];
  table_data!: MatTableDataSource<Permission>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  currentPage = 0;
  total = 0;
  sizePage = 10;

  constructor(
    private permissionsService: PermissionsService,
    public dialog: MatDialog,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.permissionsService
      .loadPaginate(this.currentPage, this.sizePage)
      .subscribe((data: any) => {
        this.total = data.totalElements;
        this.table_data = new MatTableDataSource<Permission>(data.content);
        this.table_data.paginator = this.paginator;
      });
  }

  pageChanged(event: any) {
    this.currentPage = event.pageIndex;
    this.sizePage = event.pageSize;
    this.load();
  }

  intialize() {
    this.currentPage = 0;
    this.sizePage = 10;
  }

  load() {
    this.permissionsService
      .loadPaginate(this.currentPage, this.sizePage)
      .subscribe((data: any) => {
        this.total = data.totalElements;
        this.table_data = new MatTableDataSource<Permission>(data.content);
      });
  }

  openUpdateDialog(permission: any) {
    let dialogRef = this.dialog.open(UpdatePermissionComponent, {
      data: permission,
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.load();
      }
    });
  }

  exportPermissions() {
    this.permissionsService
      .downloadAll()
      .subscribe((response: any) => this.permissionsService.saveFile(response));
  }

  onFileUploadSelected(event: any) {
    if (event.target.files.length) {
      this.permissionsService
        .upload(event.target.files[0])
        .subscribe(() =>
          this.alertService.info(
            'upload permissions',
            'You have to wait for the process to end'
          )
        );
    }
  }

  openFilterPermissionsDialog() {
    this.dialog
      .open(FilterPermissionsComponent)
      .afterClosed()
      .subscribe((filters: any) => {});
  }
}
