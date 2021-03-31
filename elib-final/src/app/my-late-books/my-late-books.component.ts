import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Book } from '../model/book.model';
import { BookServiceService } from '../services/book-service.service';

@Component({
  selector: 'app-my-late-books',
  templateUrl: './my-late-books.component.html',
  styleUrls: ['./my-late-books.component.css'],
})
export class MyLateBooksComponent implements OnInit {
  books: Book[] = [];

  constructor(
    private bookService: BookServiceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.bookService.getMyDelayedBooks().subscribe((data) => {
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
