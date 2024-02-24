import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {
  AddContentRequest,
  AddCourseRequest,
  AddMultimediaRequest,
  AddQuizRequest,
  AddTextRequest,
  AddWeekRequest,
} from 'src/app/models/Courses/Requests';
import { Activity, Week } from 'src/app/models/Courses/Week';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CourseBuilderService {
  private editModeSubject = new BehaviorSubject<boolean>(false);
  private activitySubject = new BehaviorSubject<Activity | null>(null);
  private fileTypeSubject = new BehaviorSubject<string>('');

  private weeksSubject = new BehaviorSubject<Week[]>([]);

  weeks$: Observable<Week[]> = this.weeksSubject.asObservable();

  activity$: Observable<Activity | null> = this.activitySubject.asObservable();
  fileType$: Observable<string> = this.fileTypeSubject.asObservable();

  editMode$: Observable<boolean> = this.editModeSubject.asObservable();

  private courseIdSubject = new BehaviorSubject<string>('');
  private weekIdSubject = new BehaviorSubject<string>('');

  setCourseId(courseId: string): void {
    this.courseIdSubject.next(courseId);
  }

  getCourseId(): Observable<string> {
    return this.courseIdSubject.asObservable();
  }

  setWeekId(weekId: string): void {
    this.weekIdSubject.next(weekId);
  }

  getWeekId(): Observable<string> {
    return this.weekIdSubject.asObservable();
  }

  setActivity(activity: Activity | null): void {
    this.activitySubject.next(activity);
  }

  getActivity(): Observable<Activity | null> {
    return this.activitySubject.asObservable();
  }

  setFileType(fileType: string): void {
    this.fileTypeSubject.next(fileType);
  }

  getFileType(): Observable<string> {
    return this.fileTypeSubject.asObservable();
  }

  getWeeksLength(): number {
    let length = 0;
    this.weeks$.subscribe({
      next: (res) => {
        length = res.length;
      },
    });
    return length;
  }

  setEditMode(mode: boolean): void {
    this.editModeSubject.next(mode);
  }

  getEditMode(): Observable<boolean> {
    return this.editModeSubject.asObservable();
  }
  constructor(private http: HttpClient) {}

  loadPaginate(page: number, size: number, userId: string) {
    return this.http.get(
      `${environment.apiUrl}api/course/${userId}?page=${page}&size=${size}`
    );
  }

  getWeeks(courseId: string) {
    this.http
      .get<Week[]>(`${environment.apiUrl}api/course/week/all/${courseId}`)
      .subscribe({
        next: (weeks) => {
          this.weeksSubject.next(weeks);
        },
      });
  }

  deleteWeek(weekId: string): Observable<any> {
    return this.http.delete(`${environment.apiUrl}api/course/week/${weekId}`);
  }

  add(userId: string, body: AddCourseRequest) {
    const formData = new FormData();
    formData.append('userId', userId);
    formData.append('title', body.title);
    formData.append('summary', body.summary);
    formData.append('image', body.image);
    return this.http.put(`${environment.apiUrl}api/course`, formData);
  }

  addWeek(courseId: string, body: AddWeekRequest) {
    return this.http.post(
      `${environment.apiUrl}api/course/week/${courseId}`,
      body
    );
  }
  editWeek(weekId: string, body: AddWeekRequest) {
    return this.http.put(
      `${environment.apiUrl}api/course/week/${weekId}`,
      body
    );
  }

  delete(params: any) {
    return this.http.delete(
      `${environment.apiUrl}api/course/${params.userId}/${params.courseId}`
    );
  }

  setActivitySubject(subject: BehaviorSubject<Activity | null>): void {
    this.activitySubject.next(subject.value);
  }

  /* Text Endpoints */

  addText(weekId: string, body: AddTextRequest) {
    return this.http.post(
      `${environment.apiUrl}api/course/activity/text/${weekId}`,
      body
    );
  }
  updateText(activityId: string, body: AddTextRequest) {
    const queryParams = new HttpParams()
      .set('title', body.title)
      .set('number', body.number.toString())
      .set('text', body.text);

    const url = `${
      environment.apiUrl
    }api/course/activity/text/${activityId}?${queryParams.toString()}`;

    return this.http.put(url, {});
  }
  /* Content Endpoints */

  addContent(weekId: string, body: AddContentRequest) {
    return this.http.post(
      `${environment.apiUrl}api/course/activity/content/${weekId}`,
      body
    );
  }
  updateContent(activityId: string, body: AddContentRequest) {
    const queryParams = new HttpParams()
      .set('title', body.title)
      .set('number', body.number.toString())
      .set('contentId', body.contentId);

    const url = `${
      environment.apiUrl
    }api/course/activity/content/${activityId}?${queryParams.toString()}`;

    return this.http.put(url, {});
  }
  /* Multimedia Endpoints */

  addMultimedia(weekId: string, body: AddMultimediaRequest) {
    return this.http.post(
      `${environment.apiUrl}api/course/activity/multimedia/${weekId}`,
      body
    );
  }
  updateMultimedia(activityId: string, body: AddMultimediaRequest) {
    const queryParams = new HttpParams()
      .set('title', body.title)
      .set('number', body.number.toString())
      .set('url', body.multimedia);

    const url = `${
      environment.apiUrl
    }api/course/activity/multimedia/${activityId}?${queryParams.toString()}`;

    return this.http.put(url, {});
  }
  /* Quiz Endpoints */

  addQuiz(weekId: string, body: AddQuizRequest) {
    return this.http.post(
      `${environment.apiUrl}api/course/activity/quiz/${weekId}`,
      body
    );
  }
  addQuestion(quizId: string, body: AddQuizRequest) {
    return this.http.post(
      `${environment.apiUrl}api/course/activity/quiz/question/${quizId}`,
      body
    );
  }
  addChoice(questionId: string, body: AddQuizRequest) {
    return this.http.post(
      `${environment.apiUrl}api/course/activity/quiz/question/choice/${questionId}`,
      body
    );
  }
  updateQuiz(quizId: string, body: AddQuizRequest) {
    const queryParams = new HttpParams()
      .set('title', body.title)
      .set('number', body.number.toString());

    const url = `${
      environment.apiUrl
    }api/course/activity/quiz/${quizId}?${queryParams.toString()}`;

    return this.http.put(url, body);
  }
  updateQuestion(questionId: string, body: any) {
    const queryParams = new HttpParams()
      .set('questionContent', body.questionContent)
      .set('type', body.type);

    const url = `${
      environment.apiUrl
    }api/course/activity/quiz/question/${questionId}?${queryParams.toString()}`;

    return this.http.put(url, body);
  }
  updateChoice(choiceId: string, body: any) {
    const queryParams = new HttpParams()
      .set('choice', body.choice)
      .set('isTrue', body.isTrue);

    const url = `${
      environment.apiUrl
    }api/course/activity/quiz/question/choice/${choiceId}?${queryParams.toString()}`;

    return this.http.put(url, body);
  }

  deleteActivity(activityId: string) {
    return this.http.delete(
      `${environment.apiUrl}api/course/activity/${activityId}`
    );
  }
  deleteQuestion(questionId: string) {
    return this.http.delete(
      `${environment.apiUrl}api/course/activity/quiz/question/${questionId}`
    );
  }
  deleteChoice(choiceId: string) {
    return this.http.delete(
      `${environment.apiUrl}api/course/activity/quiz/question/choice/${choiceId}`
    );
  }

  updateSettings(courseId: string, body: any) {
    return this.http.post(
      `${environment.apiUrl}api/course/setting/${courseId}`,
      body
    );
  }
}
