import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {SetupShelfmarkComponent} from './setup-shelfmark.component';

describe('SetupShelfmarkComponent', () => {
  let component: SetupShelfmarkComponent;
  let fixture: ComponentFixture<SetupShelfmarkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetupShelfmarkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetupShelfmarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /*it('should create', () => {
    expect(component).toBeTruthy();
  });*/
});
