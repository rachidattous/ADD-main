import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayAudioComponent } from './display-audio.component';

describe('DisplayAudioComponent', () => {
  let component: DisplayAudioComponent;
  let fixture: ComponentFixture<DisplayAudioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisplayAudioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayAudioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
