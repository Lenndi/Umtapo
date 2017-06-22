import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PairingBorrowerButtonComponent } from './pairing-borrower-button.component';

describe('PairingBorrowerButtonComponent', () => {
  let component: PairingBorrowerButtonComponent;
  let fixture: ComponentFixture<PairingBorrowerButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PairingBorrowerButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PairingBorrowerButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
