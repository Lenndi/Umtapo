import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PairingItemButtonComponent } from './pairing-item-button.component';

describe('PairingItemButtonComponent', () => {
  let component: PairingItemButtonComponent;
  let fixture: ComponentFixture<PairingItemButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PairingItemButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PairingItemButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
