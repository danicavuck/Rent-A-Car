import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InboxContactsComponent } from './inbox-contacts.component';

describe('InboxContactsComponent', () => {
  let component: InboxContactsComponent;
  let fixture: ComponentFixture<InboxContactsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InboxContactsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InboxContactsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
