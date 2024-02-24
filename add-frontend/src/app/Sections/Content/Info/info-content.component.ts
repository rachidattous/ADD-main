import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Content } from 'src/app/models/Content/Content';
import { ContentState } from 'src/app/models/Content/Enums';

@Component({
  selector: 'app-info-content',
  templateUrl: './info-content.component.html',
  styleUrls: ['./info-content.component.scss']
})
export class InfoContentComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public content : Content) { }

  ngOnInit(): void {
  }

  state(contentState : ContentState){
    switch(contentState){
      case ContentState.Accepted : return 'ok'; break;
      case ContentState.Refused : return 'refused'; break;
      default : return 'inprogress';
    }
  }

}
