import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from 'src/app/Common/Components/ConfirmationDialog/confirmation-dialog.component';
import { EvaluateComponent } from '../Evaluate/evaluate.component';
import { User } from 'src/app/Services/Users/User';
import { Store } from '@ngrx/store';
import { IAppState } from 'src/app/NgRx/reducers';
import { userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ContentState, FileType } from 'src/app/models/Content/Enums';
import { Observable } from 'rxjs';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { Router } from '@angular/router';
import { ContentService } from 'src/app/Services/Content/content.service';
import { InfoContentComponent } from '../Info/info-content.component';
import { FilterContentComponent } from '../Filter/filter-content.component';
import { Content } from 'src/app/models/Content/Content';
import { AddContentComponent } from '../Add/add-content.component';
import { ContentReplaceComponent } from '../Replace/content-replace.component';
import { Display } from 'src/app/Navigation/navigation';
import { Permission } from 'src/app/Services/Permissions/Permission';

@Component({
  selector: 'app-content-videos',
  templateUrl: './content-videos.component.html',
  styleUrls: ['./content-videos.component.scss']
})
export class ContentVideosComponent implements OnInit {

  searchText = ''
  suggestions : string[] = [];
  currentUser !: User | null;
  canReplaceContent : boolean = false
  canEvaluateContent : boolean = false

  videos !: MatTableDataSource<Content>
  videos$ !: Observable<Content[]>
  @ViewChild(MatPaginator) paginator !: MatPaginator;
  query = {
    currentPage : 0,
    sizePage : 10,
    total : 0
  }

  filters = { fileType : FileType.Video }
  filtersApplied: boolean = false;

  constructor(public dialog : MatDialog, 
    private store : Store<IAppState>, 
    private changeDetectorRef : ChangeDetectorRef, 
    private alertService : AlertService,
    private service : ContentService,
    private router : Router) { }

  ngOnInit(): void {
    this.changeDetectorRef.detectChanges()
    this.service.loadPaginate(this.query.currentPage, this.query.sizePage, this.filters)
    .subscribe((data : any) => {
      this.query.total = data.totalElements
      this.videos = new MatTableDataSource<Content>(data.content)
      this.videos.paginator = this.paginator
      this.videos$ = this.videos.connect()
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
      this.query.total = data.totalElements
      this.videos = new MatTableDataSource<Content>(data.content)
      this.videos$ = this.videos.connect()
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

  search(){
    if(!this.searchText) return
    this.service.loadPaginate(0, 10, {query : this.searchText, fileType : FileType.Video})
    .subscribe((data : any) => {
      this.query.total = data.totalElements;
      this.videos = new MatTableDataSource<Content>(data.content)
      this.videos$ = this.videos.connect()
    })
  }

  openAddVideoDialog(){
    this.dialog.open(AddContentComponent, {data : FileType.Video}).afterClosed()
    .subscribe(() => {
      this.load()
    })
  }

  openEvaluationDialog(video : any){
    this.dialog.open(EvaluateComponent).afterClosed()
    .subscribe((evaluationData : any) => {
      this.service.evaluateContent(video.id, { userId : this.currentUser?.id, ...evaluationData })
      .subscribe(() => {
        this.alertService.info('Video Evaluation', 'the video was evaluated successfully')
          this.load();
      })
    });
  }

  openInfoVideoDialog(video : any){
    this.dialog.open(InfoContentComponent, {data : video})
  }

  displayVideo(video : any){
    this.router.navigate(['content/videos', video.id])
  }

  openDeleteDialog(id : string | undefined){
    let ref = this.dialog.open(ConfirmationDialogComponent);
    ref.afterClosed().subscribe((confirm : boolean) => {
      if(confirm){
        this.service.delete(id || '')
        .subscribe(() => {
          this.alertService.succes('Video Delete', 'the video was deleted successfully')
          this.load()
        })
      }
    });
  }

  openFilterDialog(){
    let ref = this.dialog.open(FilterContentComponent, {data : FileType.Video});
    ref.afterClosed().subscribe((filters : any) => {
      if(filters){
        this.filtersApplied = true
        this.filters = filters
      } else {
        this.filtersApplied = false
        this.filters = { fileType : FileType.Video }
      }
      this.initialize();
      this.load();
    })
  }

  openReplaceDialog(video : Content) {
    this.dialog.open(ContentReplaceComponent, { data : video})
    .afterClosed().subscribe(() => {
      this.load()
    })
  }

  state(state : ContentState){
    switch(state){
      case ContentState.Accepted : return 'accepted'; break;
      case ContentState.Refused : return 'refused'; break;
      default : return 'pending';
    }
  }

  ngOnDestroy(): void {
    if(this.videos) this.videos.disconnect()
  }

}

