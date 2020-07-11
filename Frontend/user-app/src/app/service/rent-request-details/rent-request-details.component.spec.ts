import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentRequestDetailsComponent } from './rent-request-details.component';

describe('RentRequestDetailsComponent', () => {
  let component: RentRequestDetailsComponent;
  let fixture: ComponentFixture<RentRequestDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentRequestDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentRequestDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
