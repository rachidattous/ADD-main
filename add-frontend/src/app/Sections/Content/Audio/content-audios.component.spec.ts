import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentAudiosComponent } from './content-audios.component';

describe('ContentAudiosComponent', () => {
  let component: ContentAudiosComponent;
  let fixture: ComponentFixture<ContentAudiosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContentAudiosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentAudiosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
