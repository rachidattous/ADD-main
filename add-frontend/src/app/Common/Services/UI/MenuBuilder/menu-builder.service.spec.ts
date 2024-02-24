import { TestBed } from '@angular/core/testing';

import { MenuBuilderService } from './menu-builder.service';

describe('MenuBuilderService', () => {
  let service: MenuBuilderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MenuBuilderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
