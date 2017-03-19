/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CirculationComponent } from './circulation.component';

describe('CirculationComponent', () => {
  let component: CirculationComponent;
  let fixture: ComponentFixture<CirculationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CirculationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CirculationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
