import { ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from 'src/app/Common/Components/ConfirmationDialog/confirmation-dialog.component';
import { DisplayImageComponent } from './Display/display-image.component';
import { EvaluateComponent } from '../Evaluate/evaluate.component';
import { Store } from '@ngrx/store';
import { IAppState } from 'src/app/NgRx/reducers';
import { userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { User } from 'src/app/Services/Users/User';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { ContentState, FileType } from 'src/app/models/Content/Enums';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { ContentService } from 'src/app/Services/Content/content.service';
import { InfoContentComponent } from '../Info/info-content.component';
import { FilterContentComponent } from '../Filter/filter-content.component';
import { Content } from 'src/app/models/Content/Content';
import { AddContentComponent } from '../Add/add-content.component';
import { ContentReplaceComponent } from '../Replace/content-replace.component';
import { Permission } from 'src/app/models/Permissions/Permission';
import { Display } from 'src/app/Navigation/navigation';

@Component({
  selector: 'app-content-images',
  templateUrl: './content-images.component.html',
  styleUrls: ['./content-images.component.scss']
})
export class ContentImagesComponent implements OnInit, OnDestroy {

  searchText = ''
  suggestions : string[] = [];
  currentUser !: User | null;
  canReplaceContent : boolean = false
  canEvaluateContent : boolean = false

  images !: MatTableDataSource<Content>
  images$ !: Observable<Content[]>

  @ViewChild(MatPaginator) paginator !: MatPaginator;
  query = {
    currentPage : 0,
    sizePage : 10,
    total : 0
  }

  filters = { fileType : FileType.Image }
  filtersApplied: boolean = false;

  constructor(public dialog : MatDialog, 
    private changeDetectorRef : ChangeDetectorRef,
    private store : Store<IAppState>, 
    private service : ContentService,
    private alertService : AlertService) { }

  ngOnInit(): void {
    this.changeDetectorRef.detectChanges()
    this.service.loadPaginate(this.query.currentPage, this.query.sizePage, this.filters)
    .subscribe((data : any) => {
      this.query.total = data.totalElements;
      this.images = new MatTableDataSource<Content>(data.content)
      this.images.paginator = this.paginator
      this.images$ = this.images.connect()
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

  pageChanged(event : any){
    this.query.currentPage = event.pageIndex;
    this.query.sizePage = event.pageSize;
    this.load()
  }

  initialize(){
    this.query.currentPage = 0
    this.query.sizePage = 10
  }

  load(){
    this.service.loadPaginate(this.query.currentPage, this.query.sizePage, this.filters)
    .subscribe((data : any) => {
      this.query.total = data.totalElements;
      this.images = new MatTableDataSource<Content>(data.content)
      this.images$ = this.images.connect()
    })
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

  openReplaceDialog(image : Content) {
    this.dialog.open(ContentReplaceComponent, { data : image})
    .afterClosed().subscribe(() => {
      this.load()
    })
  }

  search(){
    if(!this.searchText) return
    this.service.loadPaginate(0, 10, {query : this.searchText, fileType : FileType.Image})
    .subscribe((data : any) => {
      this.query.total = data.totalElements;
      this.images = new MatTableDataSource<Content>(data.content)
      this.images$ = this.images.connect()
    })
  }

  openAddImageDialog(){
    this.dialog.open(AddContentComponent, {data : FileType.Image}).afterClosed()
    .subscribe(() => {
      this.load()
    })
  }

  openInfoImageDialog(image : any){
    this.dialog.open(InfoContentComponent, { data : image});
  }

  openDisplayImageDialog(image : any){
    this.dialog.open(DisplayImageComponent, { data : image});
  }

  openEvaluationDialog(image : any){
    this.dialog.open(EvaluateComponent).afterClosed()
    .subscribe((evaluationData : any) => {
      this.service.evaluateContent(image.id, { userId : this.currentUser?.id, ...evaluationData })
      .subscribe(() => {
        this.alertService.info('Image Evaluation', 'the image was evaluated successfully')
          this.load();
      })
    });
  }

  openDeleteDialog(id : string | undefined){
    let ref = this.dialog.open(ConfirmationDialogComponent);
    ref.afterClosed().subscribe((confirm : boolean) => {
      if(confirm){
        this.service.delete(id || '')
        .subscribe(() => {
          this.alertService.succes('Image Delete', 'the image was deleted successfully')
          this.load()
        })
      }
    });
  }

  openFilterDialog(){
    let ref = this.dialog.open(FilterContentComponent, {data : FileType.Image});
    ref.afterClosed().subscribe((filters : any) => {
      if(filters){
        this.filtersApplied = true
        this.filters = filters
      } else {
        this.filtersApplied = false
        this.filters = { fileType : FileType.Image }
      }
      this.initialize();
      this.load();
    })
  }

  chooseIcon(extension : string){
    switch(extension){
      case 'png' : return "assets/png.png"; break;
      case 'jpg' : return "assets/jpg.png"; break;
      default : return "assets/img.png"; 
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
    if(this.images) this.images.disconnect()
  }

}
