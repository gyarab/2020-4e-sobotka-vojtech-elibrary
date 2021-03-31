import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { AuthorServiceService } from '../services/author-service.service';
import { GenreServiceService } from '../services/genre-service.service';

@Component({
  selector: 'app-create-genre-or-author',
  templateUrl: './create-genre-or-author.component.html',
  styleUrls: ['./create-genre-or-author.component.css'],
})
export class CreateGenreOrAuthorComponent implements OnInit {
  constructor(
    private genreService: GenreServiceService,
    private authorService: AuthorServiceService
  ) {}
  authorControl = new FormGroup({
    author: new FormControl('', Validators.required),
  });
  genreControl = new FormGroup({
    genre: new FormControl('', Validators.required),
  });
  ngOnInit(): void {}

  genreSubmit(): void {
    if (this.genreControl.invalid) {
      Swal.fire('Error', 'Author must hava a name!', 'error');
      return;
    } else {
      this.genreService
        .createGenre(this.genreControl.controls['genre'].value)
        .subscribe((data) => {
          Swal.fire('Success', 'Genre created', 'success');
        });
      this.genreControl.controls['genre'].setValue('');
    }
  }

  authorSubmit(): void {
    if (this.authorControl.invalid) {
      Swal.fire('Error', 'Genre must hava a name!', 'error');
    } else {
      
      
      this.authorService
        .createAuthor(this.authorControl.controls['author'].value)
        .subscribe((data) => {
          Swal.fire('Success', 'Author created', 'success');
        });
      this.authorControl.controls['author'].setValue('');
    }
  }
}
