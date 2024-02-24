import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-display-video',
  templateUrl: './display-video.component.html',
  styleUrls: ['./display-video.component.scss']
})
export class DisplayVideoComponent implements OnInit {

  // video id
  id !: string | null

  constructor(private route : ActivatedRoute, private router : Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')
  }

  navigate(){
    this.router.navigate(['content/videos'])
  }

  stream(){
    return `${environment.apiUrl}api/file/stream/${this.id}`
  }
  
}
