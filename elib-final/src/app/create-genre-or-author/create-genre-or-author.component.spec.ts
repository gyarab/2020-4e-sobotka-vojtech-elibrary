import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGenreOrAuthorComponent } from './create-genre-or-author.component';

describe('CreateGenreOrAuthorComponent', () => {
  let component: CreateGenreOrAuthorComponent;
  let fixture: ComponentFixture<CreateGenreOrAuthorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateGenreOrAuthorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateGenreOrAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
