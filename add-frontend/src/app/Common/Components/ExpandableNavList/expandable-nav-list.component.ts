import { Component, Input, OnInit } from '@angular/core';
import { IMenuSection } from '../interfaces/IMenuSection';

@Component({
  selector: 'app-expandable-nav-list',
  templateUrl: './expandable-nav-list.component.html',
  styleUrls: ['./expandable-nav-list.component.scss']
})
export class ExpandableNavListComponent implements OnInit {

  @Input() menuSection !: IMenuSection;

  constructor() { }

  ngOnInit(): void {
  }

}
