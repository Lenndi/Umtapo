import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LibraryConfigurationComponent } from './library-configuration.component';

describe('LibraryConfigurationComponent', () => {
  let component: LibraryConfigurationComponent;
  let fixture: ComponentFixture<LibraryConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LibraryConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LibraryConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
