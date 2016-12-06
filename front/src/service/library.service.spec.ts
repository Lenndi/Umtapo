/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { LibraryService } from './library.service';

describe('LibraryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LibraryService]
    });
  });

  it('should create an instance of LibraryService', inject([LibraryService], (service: LibraryService) => {
    expect(service).toBeTruthy();
  }));
});
