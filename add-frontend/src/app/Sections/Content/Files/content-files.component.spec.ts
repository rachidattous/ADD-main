import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentFilesComponent } from './content-files.component';

describe('ContentFilesComponent', () => {
  let component: ContentFilesComponent;
  let fixture: ComponentFixture<ContentFilesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContentFilesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentFilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
