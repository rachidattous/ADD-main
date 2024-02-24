import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpandableNavListComponent } from './expandable-nav-list.component';

describe('ExpandableNavListComponent', () => {
  let component: ExpandableNavListComponent;
  let fixture: ComponentFixture<ExpandableNavListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExpandableNavListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpandableNavListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
