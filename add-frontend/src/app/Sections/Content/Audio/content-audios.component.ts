import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Store } from '@ngrx/store';
import { Observable, filter } from 'rxjs';
import { ConfirmationDialogComponent } from 'src/app/Common/Components/ConfirmationDialog/confirmation-dialog.component';
import { AlertService } from 'src/app/Common/Services/UI/Alert/alert.service';
import { userInfo$selector} from 'src/app/NgRx/User/user.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { ContentService } from 'src/app/Services/Content/content.service';
import { User } from 'src/app/Services/Users/User';
import { ContentState, FileType } from 'src/app/models/Content/Enums';
import { AddContentComponent } from '../Add/add-content.component';
import { EvaluateComponent } from '../Evaluate/evaluate.component';
import { FilterContentComponent } from '../Filter/filter-content.component';
import { InfoContentComponent } from '../Info/info-content.component';
import { Content } from 'src/app/models/Content/Content';
import { DisplayAudioComponent } from './Display/display-audio.component';
import { ContentReplaceComponent } from '../Replace/content-replace.component';
import { Display } from 'src/app/Navigation/navigation';
import { Permission } from 'src/app/Services/Permissions/Permission';

@Component({
  selector: 'app-content-audios',
  templateUrl: './content-audios.component.html',
  styleUrls: ['./content-audios.component.scss']
})
export class ContentAudiosComponent implements OnInit {

  searchText = ''
  suggestions : string[] = []
  currentUser !: User | null;
  canReplaceContent : boolean = false
  canEvaluateContent : boolean = false

  audios !: MatTableDataSource<Content>
  audios$ !: Observable<Content[]>
  @ViewChild(MatPaginator) paginator !: MatPaginator;
  query = {
    currentPage : 0,
    sizePage : 10,
    total : 0
  }

  filters : any =  { fileType : FileType.Audio }
  filtersApplied : boolean = false;

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
      this.audios = new MatTableDataSource<Content>(data.content)
      this.audios.paginator = this.paginator
      this.audios$ = this.audios.connect()
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
    this.service.loadPaginate(this.query.currentPage, this.query.sizePage, this.filters)
    .subscribe((data : any) => {
      this.query.total = data.totalElements;
      this.audios = new MatTableDataSource<Content>(data.content)
      this.audios$ = this.audios.connect()
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
    this.service.loadPaginate(0, 10, {query : this.searchText, fileType : FileType.Audio})
    .subscribe((data : any) => {
      this.query.total = data.totalElements;
      this.audios = new MatTableDataSource<Content>(data.content)
      this.audios$ = this.audios.connect()
    })
  }

  openAddAudioDialog(){
    this.dialog.open(AddContentComponent, {data : FileType.Audio}).afterClosed()
    .subscribe(() => {
      this.load()
    })
  }

  openEvaluationDialog(audio : any){
    this.dialog.open(EvaluateComponent).afterClosed()
    .subscribe((evaluationData : any) => {
      this.service.evaluateContent(audio.id, { userId : this.currentUser?.id, ...evaluationData })
      .subscribe(() => {
        this.alertService.info('Audio Evaluation', 'the audio was evaluated successfully')
          this.load();
      })
    });
  }

  openInfoAudioDialog(audio : any){
    this.dialog.open(InfoContentComponent, {data : audio})
  }

  openDisplayAudioDialog(audio : Content){
    this.dialog.open(DisplayAudioComponent, { data : audio});
  }

  openDeleteDialog(id : string | undefined){
    let ref = this.dialog.open(ConfirmationDialogComponent);
    ref.afterClosed().subscribe((confirm : boolean) => {
      if(confirm){
        this.service.delete(id || '')
        .subscribe(() => {
          this.alertService.succes('Audio Delete', 'the document was deleted successfully')
          this.load();
        })
      }
    });
  }

  openFilterDialog(){
    let ref = this.dialog.open(FilterContentComponent, {data : FileType.Audio});
    ref.afterClosed().subscribe((filters : any) => {
      if(filters){
        this.filtersApplied = true
        this.filters = filters
      } else {
        this.filtersApplied = false
        this.filters = { fileType : FileType.Audio }
      }
      this.initialize();
      this.load();
    })
  }

  openReplaceDialog(audio : Content) {
    this.dialog.open(ContentReplaceComponent, { data : audio})
    .afterClosed().subscribe(() => {
      this.load()
    })
  }

  chooseIcon(extension : string){
    switch(extension){
      case 'mp3' : return "assets/mp3.png"; break;
      default : return "assets/file.png"; 
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
    if(this.audios) this.audios.disconnect()
  }

}
