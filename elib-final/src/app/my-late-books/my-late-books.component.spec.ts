import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyLateBooksComponent } from './my-late-books.component';

describe('MyLateBooksComponent', () => {
  let component: MyLateBooksComponent;
  let fixture: ComponentFixture<MyLateBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyLateBooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyLateBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
