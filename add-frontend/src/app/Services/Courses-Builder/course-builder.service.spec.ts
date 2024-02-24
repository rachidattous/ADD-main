import { TestBed } from '@angular/core/testing';

import { CourseBuilderService } from './course-builder.service';

describe('CourseBuilderService', () => {
  let service: CourseBuilderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourseBuilderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
