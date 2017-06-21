import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministratorSignUpComponent } from './administrator-sign-up.component';

describe('AdministratorSignUpComponent', () => {
  let component: AdministratorSignUpComponent;
  let fixture: ComponentFixture<AdministratorSignUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministratorSignUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministratorSignUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
