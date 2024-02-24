import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentImagesComponent } from './content-images.component';

describe('ContentImagesComponent', () => {
  let component: ContentImagesComponent;
  let fixture: ComponentFixture<ContentImagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContentImagesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentImagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
