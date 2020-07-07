import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagingEntitiesComponent } from './managing-entities.component';

describe('ManagingEntitiesComponent', () => {
  let component: ManagingEntitiesComponent;
  let fixture: ComponentFixture<ManagingEntitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagingEntitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagingEntitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
