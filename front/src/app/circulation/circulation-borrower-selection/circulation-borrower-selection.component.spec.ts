/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CirculationBorrowerSelectionComponent } from './circulation-borrower-selection.component.ts';

describe('CirculationBorrowerSelectionComponent', () => {
  let component: CirculationBorrowerSelectionComponent;
  let fixture: ComponentFixture<CirculationBorrowerSelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CirculationBorrowerSelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CirculationBorrowerSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
