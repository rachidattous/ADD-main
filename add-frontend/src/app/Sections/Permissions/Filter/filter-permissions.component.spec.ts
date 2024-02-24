import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterPermissionsComponent } from './filter-permissions.component';

describe('FilterPermissionsComponent', () => {
  let component: FilterPermissionsComponent;
  let fixture: ComponentFixture<FilterPermissionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilterPermissionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterPermissionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
