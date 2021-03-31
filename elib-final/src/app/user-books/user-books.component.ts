import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown/multiselect.model';
import Swal from 'sweetalert2';
import { Book } from '../model/book.model';
import { BookServiceService } from '../services/book-service.service';
@Component({
  selector: 'app-user-books',
  templateUrl: './user-books.component.html',
  styleUrls: ['./user-books.component.css'],
})
export class UserBooksComponent implements OnInit {
  books: Book[] = [];

  constructor(
    private bookService: BookServiceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.bookService.getUserBooks().subscribe((data) => {
      this.books = data;
    });
    if (this.books[0]) {
      console.log('not exist');
      Swal.fire('No content', 'You have not  any borrowed books', 'error');
    }
  }
  returnBook(id: string): void {
    this.bookService.returnBook(id).subscribe((data) => {
      Swal.fire('success', data.message, 'success');
    });
  }
  details(id: string): void {
    this.router.navigate(['/createGenreOrAuthor']);
    this.router.navigate(['/bookDetail' + '/' + id]);
  }
}
