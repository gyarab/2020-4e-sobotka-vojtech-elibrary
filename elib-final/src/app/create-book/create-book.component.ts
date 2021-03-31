import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import Swal from 'sweetalert2';
import { AuthorServiceService } from '../services/author-service.service';
import { BookServiceService } from '../services/book-service.service';
import { GenreServiceService } from '../services/genre-service.service';

@Component({
  selector: 'app-create-book',
  templateUrl: './create-book.component.html',
  styleUrls: ['./create-book.component.css'],
})
export class CreateBookComponent implements OnInit {
  genreList = [];
  selectedGenres = [];
  authorList = [];
  selectedAuthors = [];
  finalAuthors: string[] = [];
  finalGenres: string[] = [];
  dropdownSettings: IDropdownSettings;
  dropdownSettings_2: IDropdownSettings;
  formGroup = new FormGroup({
    title: new FormControl('', [Validators.required]),
    describtion: new FormControl(''),
    dateOfPublish: new FormControl(''),
  });
  constructor(
    private genreService: GenreServiceService,
    private authorService: AuthorServiceService,
    private bookService: BookServiceService,
    private router: Router
  ) {}
  ngOnInit() {
    this.genreService.getAllGenres().subscribe((data) => {
      let json = JSON.stringify(data);
      this.genreList = JSON.parse(json);
    });
    this.authorService.getAllAuthors().subscribe((data) => {
      let json = JSON.stringify(data);
      this.authorList = JSON.parse(json);
    });
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 5,
      allowSearchFilter: true,
    };
    this.dropdownSettings_2 = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 5,
      allowSearchFilter: true,
    };
  }
  onItemSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }
  onSubmit(): void {
    for (let index = 0; index < this.selectedAuthors.length; index++) {
      this.finalAuthors.push(this.selectedAuthors[index].name);
    }
    for (let index = 0; index < this.selectedGenres.length; index++) {
      this.finalGenres.push(this.selectedGenres[index].name);
    }
    if (this.formGroup.valid) {
      this.bookService
        .createBook(
          this.formGroup.controls['title'].value,
          this.formGroup.controls['describtion'].value,
          this.finalAuthors,
          this.finalGenres,
          this.formGroup.controls['dateOfPublish'].value
        )
        .subscribe(
          (data) => {
            Swal.fire('Success', 'Book succesfully created', 'success');
          },
          (err) => {
            console.log(err.error.status);
            Swal.fire('Error', err.error.message, 'error');
          }
        );
    } else {
      Swal.fire('error', 'Book must have title', 'error');
    }
  }
  createGenreOrAuthor(): void {
    this.router.navigate(['/createGenreOrAuthor']);
  }
}
