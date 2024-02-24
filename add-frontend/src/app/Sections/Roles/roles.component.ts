import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { AddRoleComponent } from './Add/add-role.component';
import { UpdateRoleComponent } from './Update/update-role.component';
import { ConfirmationDialogComponent } from 'src/app/Common/Components/ConfirmationDialog/confirmation-dialog.component';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { Role } from 'src/app/Services/Roles/Role';
import { RolesService } from 'src/app/Services/Roles/roles.service';
import { FilterRolesComponent } from './Filter/filter-roles.component';

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss'],
})
export class RolesComponent implements OnInit {
  columns: string[] = ['role', 'permissions', 'actions'];
  roles: any = [];
  table_data!: MatTableDataSource<Role>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  currentPage = 0;
  total = 0;
  sizePage = 10;

  constructor(
    public dialog: MatDialog,
    public alertService: AlertService,
    private rolesService: RolesService
  ) {}

  ngOnInit(): void {
    // get data from backend
    this.rolesService
      .loadPaginate(this.currentPage, this.sizePage)
      .subscribe((data: any) => {
        this.total = data.totalElements;
        this.table_data = new MatTableDataSource<Role>(data.content);
        this.table_data.paginator = this.paginator;
      });
  }

  pageChanged(event: any) {
    // handle changes here : load from backend
    this.currentPage = event.pageIndex;
    this.sizePage = event.pageSize;
    this.load();
  }

  intialize() {
    this.currentPage = 0;
    this.sizePage = 10;
  }

  load() {
    this.rolesService
      .loadPaginate(this.currentPage, this.sizePage)
      .subscribe((data: any) => {
        this.total = data.totalElements;
        this.table_data = new MatTableDataSource<Role>(data.content);
      });
  }

  onFileUploadSelected(event: any) {
    if (event.target.files.length) {
      this.rolesService.upload(event.target.files[0]);
    }
  }

  delete(id: any) {
    this.rolesService.delete(id).subscribe(() => {
      this.alertService.succes('Delete Role', 'Role was deleted successfully');
      this.load();
    });
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(AddRoleComponent);
    dialogRef.afterClosed().subscribe(() => {
      this.load();
    });
  }

  openUpdateDialog(role: any) {
    const dialogRef = this.dialog.open(UpdateRoleComponent, { data: role });
    dialogRef.afterClosed().subscribe(() => {
      this.load();
    });
  }

  openDeleteDialog(id: any) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.afterClosed().subscribe((confirm: boolean) => {
      if (confirm) {
        this.delete(id);
      }
    });
  }

  exportRoles() {
    this.rolesService
      .downloadAll()
      .subscribe((response: any) => this.rolesService.saveFile(response));
  }

  openFilterRolesDialog() {
    this.dialog
      .open(FilterRolesComponent)
      .afterClosed()
      .subscribe((filters) => {});
  }
}
