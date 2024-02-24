import { Component, Input, OnInit } from '@angular/core';
import { IMenuItem } from '../interfaces/IMenuItem';

@Component({
  selector: 'app-navigation-list',
  templateUrl: './navigation-list.component.html',
  styleUrls: ['./navigation-list.component.scss']
})
export class NavigationListComponent implements OnInit {

  @Input() items !: IMenuItem[] 

  constructor() { }

  ngOnInit(): void {
  }

}
