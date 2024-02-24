import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-expandable-list',
  templateUrl: './expandable-list.component.html',
  styleUrls: ['./expandable-list.component.scss']
})
export class ExpandableListComponent implements OnInit {

  @Input() title !: string;

  constructor() { }

  ngOnInit(): void {
  }

}
