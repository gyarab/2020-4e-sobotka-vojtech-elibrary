import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import Swal from 'sweetalert2';
import { Book } from '../model/book.model';
import { AuthorServiceService } from '../services/author-service.service';
import { BookServiceService } from '../services/book-service.service';
import { GenreServiceService } from '../services/genre-service.service';

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.css'],
})
export class UpdateBookComponent implements OnInit {
  id: null;
  book: Book = {
    id: null,
    authors: null,
    genres: null,
    title: null,
    belongsToUser: null,
    description: null,
    returnDate: null,
  };
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
    private activedRoute: ActivatedRoute,
    private bookService: BookServiceService,
    private genreService: GenreServiceService,
    private authorService: AuthorServiceService
  ) {}

  ngOnInit(): void {
    this.activedRoute.params.subscribe((params) => (this.id = params.id));
    console.log(this.id);
    this.bookService.findById(this.id).subscribe((data) => {
      this.book = data;
      console.log(data);
      console.log(this.book);
      this.selectedGenres = this.book.genres;
      this.selectedAuthors = this.book.authors;
      console.log(this.selectedGenres);
      this.formGroup.controls['title'].setValue(data.title);
      this.formGroup.controls['describtion'].setValue(data.description);
      this.formGroup.controls['dateOfPublish'].setValue(data.dateOfPublish);
    });
    this.genreService.getAllGenres().subscribe((data) => {
      let json = JSON.stringify(data);
      this.genreList = JSON.parse(json);
      console.log(json);
    });
    this.authorService.getAllAuthors().subscribe((data) => {
      let json = JSON.stringify(data);
      this.authorList = JSON.parse(json);
      console.log(json);
    });
    console.log(this.book.title);

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
  createGenreOrAuthor(): void {
    
  }
  onSubmit(): void {
    console.log(this.formGroup.controls['describtion'].value);
    for (let index = 0; index < this.selectedAuthors.length; index++) {
      console.log(this.selectedAuthors[index].name);
      this.finalAuthors.push(this.selectedAuthors[index].name);
    }
    for (let index = 0; index < this.selectedGenres.length; index++) {
      this.finalGenres.push(this.selectedGenres[index].name);
    }
    console.log(this.finalGenres);
    this.bookService
      .updateBook(
        this.id,
        this.formGroup.controls['title'].value,
        this.formGroup.controls['describtion'].value,
        this.finalAuthors,
        this.finalGenres,
        this.formGroup.controls['dateOfPublish'].value
      )
      .subscribe(
        (data) => {
          Swal.fire('Success', data.message, 'success');
        },
        (err) => {
          Swal.fire('Error', err.message, 'success');
        }
      );
  }
}
