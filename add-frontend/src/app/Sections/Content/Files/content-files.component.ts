import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { ConfirmationDialogComponent } from 'src/app/Common/Components/ConfirmationDialog/confirmation-dialog.component';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { ContentService } from 'src/app/Services/Content/content.service';
import { User } from 'src/app/Services/Users/User';
import { ContentState, FileType } from 'src/app/models/Content/Enums';
import { AddContentComponent } from '../Add/add-content.component';
import { EvaluateComponent } from '../Evaluate/evaluate.component';
import { FilterContentComponent } from '../Filter/filter-content.component';
import { InfoContentComponent } from '../Info/info-content.component';
import { Content } from 'src/app/models/Content/Content';
import { ContentReplaceComponent } from '../Replace/content-replace.component';
import { Display } from 'src/app/Navigation/navigation';
import { Permission } from 'src/app/Services/Permissions/Permission';

@Component({
  selector: 'app-content-files',
  templateUrl: './content-files.component.html',
  styleUrls: ['./content-files.component.scss']
})
export class ContentFilesComponent implements OnInit {

  searchText = ''
  suggestions : string[] = [];
  currentUser !: User | null;
  canReplaceContent : boolean = false
  canEvaluateContent : boolean = false

  files !: MatTableDataSource<Content>
  files$ !: Observable<Content[]>
  @ViewChild(MatPaginator) paginator !: MatPaginator;
  query = {
    currentPage : 0,
    sizePage : 10,
    total : 0
  }
  
  filters : any =  { fileType : FileType.File }
  filtersApplied: boolean = false;

  constructor(public dialog : MatDialog, 
    private store : Store<IAppState>, 
    private changeDetectorRef : ChangeDetectorRef,
    private service : ContentService,
    private alertService : AlertService) { }

  ngOnInit(): void {
    this.changeDetectorRef.detectChanges()
    this.service.loadPaginate(this.query.currentPage, this.query.sizePage, this.filters)
    .subscribe((data : any) => {
      this.query.total = data.totalElements;
      this.files = new MatTableDataSource<Content>(data.content)
      this.files.paginator = this.paginator
      this.files$ = this.files.connect()
    })
    this.store.select(userInfo$selector).subscribe((user : User | null) => {
      this.currentUser = user;
      user?.roles.flatMap((role : any) => role.permissions).forEach((p : Permission) => {
        switch(p.permission){
          case Display.SHOW_EVALUATE_CONTENT : this.canEvaluateContent = true; break;
          case Display.SHOW_REPLACE_CONTENT : this.canReplaceContent = true; break;
        }
      })
    })
  }

  initialize(){
    this.query.currentPage = 0
    this.query.sizePage = 10
  }

  load(){
    this.service.loadPaginate(this.query.currentPage, this.query.sizePage, this.filters )
    .subscribe((data : any) => {
      this.query.total = data.totalElements;
      this.files = new MatTableDataSource<Content>(data.content)
      this.files$ = this.files.connect()
    })
  }

  pageChanged(event : any){
    this.query.currentPage = event.pageIndex;
    this.query.sizePage = event.pageSize;
    this.load()
  }

  loadSuggestions(){
    if(!this.searchText || !this.searchText.trim().length){
      this.load()
      return
    }
    this.service.loadSuggestions(0, 10, {query : this.searchText, criteria : []})
    .subscribe((data : any) => {
      this.suggestions = data.content
    })
  }

  setSearchText(value : string){
    this.searchText = value
  }

  search(){
    if(!this.searchText) return
    this.service.loadPaginate(0, 10, {query : this.searchText, fileType : FileType.File})
    .subscribe((data : any) => {
      this.query.total = data.totalElements;
      this.files = new MatTableDataSource<Content>(data.content)
      this.files$ = this.files.connect()
    })
  }

  openAddFileDialog(){
    this.dialog.open(AddContentComponent, {data : FileType.File}).afterClosed()
    .subscribe(() => {
      this.load()
    })
  }

  openEvaluationDialog(file : any){
    this.dialog.open(EvaluateComponent).afterClosed()
    .subscribe((evaluationData : any) => {
      this.service.evaluateContent(file.id, { userId : this.currentUser?.id, ...evaluationData })
      .subscribe(() => {
        this.alertService.info('File Evaluation', 'the file was evaluated successfully')
          this.load();
      })
    });
  }

  openInfoFileDialog(file : any){
    this.dialog.open(InfoContentComponent, {data : file})
  }

  download(id : string | undefined, extension : string){
    this.service.download(id || '')
    .subscribe((response : any) => {
      this.service.saveFile(response, extension)
    })
  }

  openDeleteDialog(id : string | undefined){
    let ref = this.dialog.open(ConfirmationDialogComponent);
    ref.afterClosed().subscribe((confirm : boolean) => {
      if(confirm){
        this.service.delete(id || '')
        .subscribe(() => {
          this.alertService.succes('Document Delete', 'the file was deleted successfully')
          this.load();
        })
      }
    });
  }

  openFilterDialog(){
    let ref = this.dialog.open(FilterContentComponent, {data : FileType.File});
    ref.afterClosed().subscribe((filters : any) => {
      if(filters){
        this.filtersApplied = true
        this.filters = filters
      } else {
        this.filtersApplied = false
        this.filters = { fileType : FileType.File }
      }
      this.initialize();
      this.load();
    })
  }

  openReplaceDialog(file : Content) {
    this.dialog.open(ContentReplaceComponent, { data : file})
    .afterClosed().subscribe(() => {
      this.load()
    })
  }

  chooseIcon(extension : string){
    switch(extension){
      default : return "assets/dossier.png"; 
    }
  }

  state(state : ContentState){
    switch(state){
      case ContentState.Accepted : return 'accepted'; break;
      case ContentState.Refused : return 'refused'; break;
      default : return 'pending';
    }
  }

  ngOnDestroy(): void {
    if(this.files) this.files.disconnect()
  }

}
