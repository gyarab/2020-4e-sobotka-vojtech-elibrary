import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown/multiselect.model';
import Swal from 'sweetalert2';
import { Book } from '../model/book.model';
import { AuthorServiceService } from '../services/author-service.service';
import { BookServiceService } from '../services/book-service.service';
import { GenreServiceService } from '../services/genre-service.service';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css'],
})
export class BooksComponent implements OnInit {
  isAdmin = false;
  books: Book[];
  dropdownSettings: IDropdownSettings;
  dropdownSettings_2: IDropdownSettings;
  genreList = [];
  selectedGenres = [];
  authorList = [];
  selectedAuthors = [];
  finalAuthors: string[] = [];
  finalGenres: string[] = [];

  constructor(
    private bookService: BookServiceService,
    private formBuilder: FormBuilder,
    private router: Router,
    private genreService: GenreServiceService,
    private authorService: AuthorServiceService
  ) {}
  searchForm = this.formBuilder.group({ input: '' });
  ngOnInit(): void {
    var roles: string[] = JSON.parse(window.localStorage.getItem('USER_ROLE'));
    if (roles.indexOf('ROLE_ADMIN') > -1) {
      console.log('isAdmin')
      this.isAdmin = true;
    }
    //Defaul List of Books
    this.bookService.getAllBooks().subscribe((data) => {
      this.books = data;
      
    });
    // Methods requiered to use MultipleChoice
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
  doFilter(): void {
    this.finalAuthors = [];
    this.finalGenres = [];
    for (let index = 0; index < this.selectedAuthors.length; index++) {
      console.log(this.selectedAuthors[index].name);
      this.finalAuthors.push(this.selectedAuthors[index].name);
    }
    for (let index = 0; index < this.selectedGenres.length; index++) {
      this.finalGenres.push(this.selectedGenres[index].name);
    }

    this.bookService
      .filterBooks(this.finalAuthors, this.finalGenres)
      .subscribe((data) => {
        this.books = data;
      });
  }
  onSelectAll(items: any) {
    console.log(items);
  }
  search(): void {
    if (this.searchForm.controls['input'].value === '') {
      return;
    }
    this.bookService
      .findByTitle(this.searchForm.controls['input'].value)
      .subscribe((data) => {
        this.books = data;
      });
    window.location.reload;
  }
  details(id: string): void {
    this.router.navigate(['/bookDetail' + '/' + id]);
  }
  delete(id: string): void {
    Swal.fire({
      title: 'Warning?',
      text: 'Do you really want to delete this book?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Delete!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.bookService.deleteById(id).subscribe((data) => console.log(data));
        window.location.reload();
      }
    });
  }
  update(id: string){
    this.router.navigate(['/bookUpdate' + '/' + id]);
    
  }
}
