import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TroubleshootLayoutComponent } from './troubleshoot-layout.component';

describe('TroubleshootLayoutComponent', () => {
  let component: TroubleshootLayoutComponent;
  let fixture: ComponentFixture<TroubleshootLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TroubleshootLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TroubleshootLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
