import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentVideosComponent } from './content-videos.component';

describe('ContentVideosComponent', () => {
  let component: ContentVideosComponent;
  let fixture: ComponentFixture<ContentVideosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContentVideosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentVideosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
