import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CalendarComponent } from './Calendar/calendar.component';
import { HomeComponent } from './Home/home.component';
import { PermissionsComponent } from './Permissions/permissions.component';
import { ProfileComponent } from './Profile/profile.component';
import { RolesComponent } from './Roles/roles.component';
import { SettingsComponent } from './Settings/settings.component';
import { UsersComponent } from './Users/users.component';
import { ContentVideosComponent } from './Content/Videos/content-videos.component';
import { ContentImagesComponent } from './Content/Images/content-images.component';
import { DisplayVideoComponent } from './Content/Videos/Display/display-video.component';
import { ContentDocumentsComponent } from './Content/Documents/content-documents.component';
import { ContentAudiosComponent } from './Content/Audio/content-audios.component';
import { ContentFilesComponent } from './Content/Files/content-files.component';
import { CoursesComponent } from './Course-Builder/courses.component';
import { BuildCourseComponent } from './Course-Builder/Build/build-course.component';
import { TextFormComponent } from './Course-Builder/Build/TextForm/text-form.component';
import { MultimediaFormComponent } from './Course-Builder/Build/MultimediaForm/multimedia-form.component';
import { ContentFormComponent } from './Course-Builder/Build/ContentForm/content-form.component';
import { QuizFormComponent } from './Course-Builder/Build/QuizForm/quiz-form.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'calendar', component: CalendarComponent },
  { path: 'settings', component: SettingsComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'users', component: UsersComponent },
  { path: 'roles', component: RolesComponent },
  { path: 'permissions', component: PermissionsComponent },
  { path: 'content/videos', component: ContentVideosComponent },
  { path: 'content/videos/:id', component: DisplayVideoComponent },
  { path: 'content/images', component: ContentImagesComponent },
  { path: 'content/documents', component: ContentDocumentsComponent },
  { path: 'content/audios', component: ContentAudiosComponent },
  { path: 'content/files', component: ContentFilesComponent },
  { path: 'builder/courses', component: CoursesComponent },
  {
    path: 'builder/courses/:id',
    component: BuildCourseComponent,
    children: [
      { path: ':weekId/text', component: TextFormComponent },
      { path: ':weekId/text/:activityId/edit', component: TextFormComponent },
      { path: ':weekId/multimedia', component: MultimediaFormComponent },
      {
        path: ':weekId/multimedia/:activityId/edit',
        component: MultimediaFormComponent,
      },
      { path: ':weekId/document', component: ContentFormComponent },
      {
        path: ':weekId/document/:activityId/edit',
        component: ContentFormComponent,
      },
      { path: ':weekId/audio', component: ContentFormComponent },
      {
        path: ':weekId/audio/:activityId/edit',
        component: ContentFormComponent,
      },
      { path: ':weekId/video', component: ContentFormComponent },
      {
        path: ':weekId/video/:activityId/edit',
        component: ContentFormComponent,
      },
      { path: ':weekId/file', component: ContentFormComponent },
      {
        path: ':weekId/file/:activityId/edit',
        component: ContentFormComponent,
      },
      { path: ':weekId/image', component: ContentFormComponent },
      {
        path: ':weekId/image/:activityId/edit',
        component: ContentFormComponent,
      },
      { path: ':weekId/content', component: ContentFormComponent },
      {
        path: ':weekId/content/:activityId/edit',
        component: ContentFormComponent,
      },
      { path: ':weekId/quiz', component: QuizFormComponent },
      {
        path: ':weekId/quiz/:activityId/edit',
        component: QuizFormComponent,
      },
    ],
  },
  { path: '**', redirectTo: '/error/404', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SectionsRoutingModule {}
