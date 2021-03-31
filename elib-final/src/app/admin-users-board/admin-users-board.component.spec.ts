import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminUsersBoardComponent } from './admin-users-board.component';

describe('AdminUsersBoardComponent', () => {
  let component: AdminUsersBoardComponent;
  let fixture: ComponentFixture<AdminUsersBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminUsersBoardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminUsersBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
