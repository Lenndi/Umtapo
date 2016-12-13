/* tslint:disable:no-unused-variable */
import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {SetupVariousComponent} from './setup-various.component';

describe('SetupVariousComponent', () => {
  let component: SetupVariousComponent;
  let fixture: ComponentFixture<SetupVariousComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetupVariousComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetupVariousComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /*it('should create', () => {
    expect(component).toBeTruthy();
  });*/
});
