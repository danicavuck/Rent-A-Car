import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentRequestsComponent } from './rent-requests.component';

describe('RentRequestsComponent', () => {
  let component: RentRequestsComponent;
  let fixture: ComponentFixture<RentRequestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentRequestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
