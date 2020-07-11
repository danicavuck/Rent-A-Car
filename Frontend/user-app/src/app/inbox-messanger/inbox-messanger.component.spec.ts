import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InboxMessangerComponent } from './inbox-messanger.component';

describe('InboxMessangerComponent', () => {
  let component: InboxMessangerComponent;
  let fixture: ComponentFixture<InboxMessangerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InboxMessangerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InboxMessangerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
