import {
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { AddCourseComponent } from './Add/add-course.component';
import { MatDialog } from '@angular/material/dialog';
import { CoursesFilterComponent } from './Filter/courses-filter.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { Course } from 'src/app/models/Courses/Course';
import { CourseBuilderService } from 'src/app/Services/Courses-Builder/course-builder.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ContentService } from 'src/app/Services/Content/content.service';
import { BuildCourseComponent } from './Build/build-course.component';
import { Router } from '@angular/router';
import { userInfo$selector } from 'src/app/NgRx/User/user.selectors';
import { Store } from '@ngrx/store';
import { IAppState } from 'src/app/NgRx/reducers';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit, OnDestroy {
  courses!: MatTableDataSource<Course>;
  courses$!: Observable<Course[]>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  filters: any = {};
  filtersApplied: boolean = false;
  query: any = { currentPage: 0, sizePage: 10, total: 0 };
  currentUserId!: string;

  constructor(
    private dialog: MatDialog,
    private changeDetectorRef: ChangeDetectorRef,
    private service: CourseBuilderService,
    private contentService: ContentService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private store: Store<IAppState>
  ) {}

  ngOnInit(): void {
    this.store.select(userInfo$selector).subscribe((user: any) => {
      this.currentUserId = user.id;
    });
    this.changeDetectorRef.detectChanges();
    this.service
      .loadPaginate(
        this.query.currentPage,
        this.query.sizePage,
        this.currentUserId
      )
      // .loadPaginate(this.query.currentPage, this.query.sizePage, this.filters)
      .subscribe((data: any) => {
        this.loadBanners(data);
        this.query.total = data.totalElements;
        this.courses = new MatTableDataSource<Course>(data.content);
        this.courses.paginator = this.paginator;
        this.courses$ = this.courses.connect();
      });
  }

  initialize() {
    this.query.currentPage = 0;
    this.query.sizePage = 10;
  }

  pageChanged(event: any) {
    this.query.currentPage = event.pageIndex;
    this.query.sizePage = event.pageSize;
    this.load();
  }

  load() {
    this.service
      .loadPaginate(
        this.query.currentPage,
        this.query.sizePage,
        this.currentUserId
      )
      .subscribe((data: any) => {
        this.loadBanners(data);
        this.query.total = data.totalElements;
        this.courses = new MatTableDataSource<Course>(data.content);
        this.courses$ = this.courses.connect();
      });
  }

  loadBanners(data: any) {
    data.content.forEach((course: Course) => {
      this.contentService.stream(course.imageId).subscribe((response: any) => {
        course.imageData = response.body;
      });
    });
  }

  openAddDialog() {
    this.dialog
      .open(AddCourseComponent)
      .afterClosed()
      .subscribe(() => {
        this.load();
      });
  }

  openFilterCoursesDialog() {
    this.dialog
      .open(CoursesFilterComponent)
      .afterClosed()
      .subscribe((filters: any) => {
        if (filters) {
          this.filtersApplied = true;
          this.filters = filters;
        } else {
          this.filtersApplied = false;
          this.filters = {};
        }
        this.initialize();
        this.load();
      });
  }

  openBuildDialog() {
    this.dialog
      .open(BuildCourseComponent)
      .afterClosed()
      .subscribe(() => {
        this.load();
      });
  }

  navigateToCourse(courseId: any) {
    this.router.navigate(['/builder/courses', courseId]);
  }

  ngOnDestroy(): void {
    if (this.courses) this.courses.disconnect();
  }

  getSafeImageUrl(imageData: Blob | undefined): any {
    if (!imageData) return null;
    const imageUrl = URL.createObjectURL(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
  }
}
