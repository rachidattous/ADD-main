import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentDocumentsComponent } from './content-documents.component';

describe('ContentDocumentsComponent', () => {
  let component: ContentDocumentsComponent;
  let fixture: ComponentFixture<ContentDocumentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContentDocumentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
