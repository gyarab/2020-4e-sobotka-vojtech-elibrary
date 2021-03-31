import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LateBooksComponent } from './late-books.component';

describe('LateBooksComponent', () => {
  let component: LateBooksComponent;
  let fixture: ComponentFixture<LateBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LateBooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LateBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
