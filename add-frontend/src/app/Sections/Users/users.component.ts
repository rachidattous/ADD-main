import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { MatDialog } from '@angular/material/dialog';
import { AddUserComponent } from './Add/add-user.component';
import { UpdateUserComponent } from './Update/update-user.component';
import { ConfirmationDialogComponent } from 'src/app/Common/Components/ConfirmationDialog/confirmation-dialog.component';
import { User } from 'src/app/Services/Users/User';
import { UsersService } from 'src/app/Services/Users/users.service';
import { FilterUsersComponent } from './Filter/filter-users.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
})
export class UsersComponent implements OnInit {
  searchText = '';

  columns: string[] = [
    'firstname',
    'lastname',
    'username',
    'state',
    'role',
    'actions',
  ];
  users: any = [];
  table_data!: MatTableDataSource<User>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  currentPage = 0;
  total = 0;
  sizePage = 10;

  filters: any = {};
  filtersApplied: boolean = false;

  constructor(
    private alertService: AlertService,
    public dialog: MatDialog,
    private usersService: UsersService
  ) {}

  ngOnInit(): void {
    // get data from backend
    this.usersService
      .loadPaginate(this.currentPage, this.sizePage, this.filters)
      .subscribe((data: any) => {
        this.total = data.totalElements;
        this.table_data = new MatTableDataSource<User>(data.content);
        this.table_data.paginator = this.paginator;
      });
  }

  search() {
    if (!this.searchText) return;
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
    this.usersService
      .loadPaginate(this.currentPage, this.sizePage, this.filters)
      .subscribe((data: any) => {
        this.total = data.totalElements;
        this.table_data = new MatTableDataSource<User>(data.content);
      });
  }

  // delete user
  delete(id: any) {
    this.usersService.delete(id).subscribe(() => {
      this.alertService.succes('USER', 'the user was deleted successfully');
      this.intialize();
      this.load();
    });
  }

  onFileUploadSelected(event: any) {
    if (event.target.files.length) {
      this.usersService.upload(event.target.files[0]);
    }
  }

  openAddDialog() {
    const dialogRef = this.dialog.open(AddUserComponent);
    dialogRef.afterClosed().subscribe(() => {
      this.load();
    });
  }

  openUpdateDialog(user: any) {
    const dialogRef = this.dialog.open(UpdateUserComponent, { data: user });
    dialogRef.afterClosed().subscribe(() => {
      this.load();
    });
  }

  openDeleteDialog(id: any) {
    let ref = this.dialog.open(ConfirmationDialogComponent);
    ref.afterClosed().subscribe((confirm: boolean) => {
      if (confirm) {
        this.delete(id);
      }
    });
  }

  exportUsers() {
    this.usersService
      .downloadAll()
      .subscribe((response: any) => this.usersService.saveFile(response));
  }

  openFilterUsersDialog() {
    this.dialog
      .open(FilterUsersComponent)
      .afterClosed()
      .subscribe((filters: any) => {
        if (filters) {
          this.filters = filters;
          this.filtersApplied = true;
        } else {
          this.filters = {};
          this.filtersApplied = false;
        }
        this.intialize();
        this.load();
      });
  }
}
