import { Component, OnInit } from '@angular/core';
import { DayPilot } from '@daypilot/daypilot-lite-angular';
import { Store } from '@ngrx/store';
import { language$selector } from 'src/app/NgRx/Language/language.selectors';
import { IAppState } from 'src/app/NgRx/reducers';
import { CalendarService } from 'src/app/Services/Calendar/calendar.service';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss'],
})
export class CalendarComponent implements OnInit {

  events : DayPilot.EventData[] = []

  config : DayPilot.CalendarConfig = {
    viewType : "WorkWeek",
    headerDateFormat: "ddd M/d/yyyy",
  }

  constructor(private service : CalendarService, private store : Store<IAppState>) { }

  ngOnInit(): void {
    this.store.select(language$selector).subscribe((lang : any) => {
      this.config = {...this.config, locale : lang.local}
    })
    // load events for current user
  }

}
