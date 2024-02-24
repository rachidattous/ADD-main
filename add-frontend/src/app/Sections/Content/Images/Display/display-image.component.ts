import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { ContentService } from 'src/app/Services/Content/content.service';
import { Content } from 'src/app/models/Content/Content';

@Component({
  selector: 'app-display-image',
  templateUrl: './display-image.component.html',
  styleUrls: ['./display-image.component.scss']
})
export class DisplayImageComponent implements OnInit {

  display : any

  constructor(@Inject(MAT_DIALOG_DATA) public image: Content, private service : ContentService, private sanitizer: DomSanitizer) 
  {
    this.service.stream(this.image.id).subscribe((data : any) => {
      let objectURL = URL.createObjectURL(data.body);
      this.display = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    })
  }

  ngOnInit(): void {

  }

}
