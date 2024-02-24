import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentReplaceComponent } from './content-replace.component';

describe('ContentReplaceComponent', () => {
  let component: ContentReplaceComponent;
  let fixture: ComponentFixture<ContentReplaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContentReplaceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentReplaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
