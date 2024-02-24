import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';

import { SectionsRoutingModule } from './sections-routing.module';
import { HomeComponent } from './Home/home.component';
import { CalendarComponent } from './Calendar/calendar.component';
import { MaterialModule } from '../Common/Modules/Material/material.module';
import { SettingsComponent } from './Settings/settings.component';
import { ProfileComponent } from './Profile/profile.component';
import { ChatbotComponent } from './Chatbot/chatbot.component';
import { PermissionsComponent } from './Permissions/permissions.component';
import { RolesComponent } from './Roles/roles.component';
import { UsersComponent } from './Users/users.component';
import { ComponentsModule } from '../Common/Components/components.module';
import { UpdatePermissionComponent } from './Permissions/Update/update-permission.component';
import { AddRoleComponent } from './Roles/Add/add-role.component';
import { UpdateRoleComponent } from './Roles/Update/update-role.component';
import { AddUserComponent } from './Users/Add/add-user.component';
import { UpdateUserComponent } from './Users/Update/update-user.component';
import { ContentVideosComponent } from './Content/Videos/content-videos.component';
import { ContentImagesComponent } from './Content/Images/content-images.component';
import { DisplayImageComponent } from './Content/Images/Display/display-image.component';
import { EvaluateComponent } from './Content/Evaluate/evaluate.component';
import { FilterPermissionsComponent } from './Permissions/Filter/filter-permissions.component';
import { FilterRolesComponent } from './Roles/Filter/filter-roles.component';
import { FilterUsersComponent } from './Users/Filter/filter-users.component';
import { NgxSliderModule } from '@angular-slider/ngx-slider';
import { DisplayVideoComponent } from './Content/Videos/Display/display-video.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ContentAudiosComponent } from './Content/Audio/content-audios.component';
import { InfoContentComponent } from './Content/Info/info-content.component';
import { FilterContentComponent } from './Content/Filter/filter-content.component';
import { ContentDocumentsComponent } from './Content/Documents/content-documents.component';
import { AddContentComponent } from './Content/Add/add-content.component';
import { DisplayAudioComponent } from './Content/Audio/Display/display-audio.component';
import { ContentFilesComponent } from './Content/Files/content-files.component';
import { ContentReplaceComponent } from './Content/Replace/content-replace.component';
import { CoursesComponent } from './Course-Builder/courses.component';
import { AddCourseComponent } from './Course-Builder/Add/add-course.component';
import { CoursesFilterComponent } from './Course-Builder/Filter/courses-filter.component';
import { DayPilotModule } from '@daypilot/daypilot-lite-angular';
import { BuildCourseComponent } from './Course-Builder/Build/build-course.component';
import { AddWeekComponent } from './Course-Builder/Build/AddWeek/add-week.component';
import { WeeksSidebarComponent } from './Course-Builder/Build/WeeksSidebar/weeks-sidebar.component';
import { WeekComponent } from './Course-Builder/Build/WeeksSidebar/week/week.component';
import { EditWeekComponent } from './Course-Builder/Build/EditWeek/edit-week.component';
import { NgPipesModule } from 'ngx-pipes';
import { TextFormComponent } from './Course-Builder/Build/TextForm/text-form.component';
import { MultimediaFormComponent } from './Course-Builder/Build/MultimediaForm/multimedia-form.component';
import { ContentFormComponent } from './Course-Builder/Build/ContentForm/content-form.component';
import { RouterModule } from '@angular/router';
import { QuizFormComponent } from './Course-Builder/Build/QuizForm/quiz-form.component';
import { QuillModule } from 'ngx-quill';
import { ChangeSettingsComponent } from './Course-Builder/Build/ChangeSettings/change-settings.component';

@NgModule({
  declarations: [
    HomeComponent,
    CalendarComponent,
    CalendarComponent,
    SettingsComponent,
    ProfileComponent,
    ChatbotComponent,
    PermissionsComponent,
    RolesComponent,
    UsersComponent,
    UpdatePermissionComponent,
    AddRoleComponent,
    UpdateRoleComponent,
    AddUserComponent,
    UpdateUserComponent,
    ContentVideosComponent,
    ContentImagesComponent,
    DisplayImageComponent,
    EvaluateComponent,
    FilterPermissionsComponent,
    FilterRolesComponent,
    FilterUsersComponent,
    DisplayVideoComponent,
    ContentAudiosComponent,
    InfoContentComponent,
    FilterContentComponent,
    ContentDocumentsComponent,
    AddContentComponent,
    DisplayAudioComponent,
    ContentFilesComponent,
    ContentReplaceComponent,
    CoursesComponent,
    AddCourseComponent,
    CoursesFilterComponent,
    BuildCourseComponent,
    AddWeekComponent,
    WeeksSidebarComponent,
    WeekComponent,
    EditWeekComponent,
    TextFormComponent,
    MultimediaFormComponent,
    ContentFormComponent,
    QuizFormComponent,
    ChangeSettingsComponent,
  ],
  imports: [
    CommonModule,
    SectionsRoutingModule,
    ReactiveFormsModule,
    MaterialModule,
    FormsModule,
    ComponentsModule,
    NgxMatSelectSearchModule,
    NgxSliderModule,
    DragDropModule,
    DayPilotModule,
    NgPipesModule,
    RouterModule,
    QuillModule.forRoot({
      modules: {
        toolbar: [
          ['bold', 'italic', 'underline', 'strike'], // toggled buttons
          ['blockquote', 'code-block'],
          [{ header: 1 }, { header: 2 }], // custom button values
          [{ list: 'ordered' }, { list: 'bullet' }],
          [{ script: 'sub' }, { script: 'super' }], // superscript/subscript
          [{ indent: '-1' }, { indent: '+1' }], // outdent/indent
          [{ direction: 'rtl' }], // text direction

          [{ size: ['small', false, 'large', 'huge'] }], // custom dropdown
          [{ header: [1, 2, 3, 4, 5, 6, false] }],
          [{ color: [] }, { background: [] }], // dropdown with defaults from theme
          [{ font: [] }],
          [{ align: [] }],
          ['clean'], // remove formatting button
          ['link'], // link and image, video
        ],
      },
    }),
  ],
  exports: [ChatbotComponent],
})
export class SectionsModule {}
