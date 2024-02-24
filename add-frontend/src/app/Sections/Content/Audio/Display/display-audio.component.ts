import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Content } from 'src/app/models/Content/Content';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-display-audio',
  templateUrl: './display-audio.component.html',
  styleUrls: ['./display-audio.component.scss']
})
export class DisplayAudioComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public audio : Content) { }

  ngOnInit(): void {
  }

  stream(){
    return `${environment.apiUrl}api/file/stream/${this.audio.id}`
  }

}
