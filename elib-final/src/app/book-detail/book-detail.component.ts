import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { Book } from '../model/book.model';
import { BookServiceService } from '../services/book-service.service';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css'],
})
export class BookDetailComponent implements OnInit {
  available = 'unavailable';
  isBorrowable = false;
  id = null;
  book: Book = {
    id: null,
    authors: null,
    genres: null,
    title: null,
    belongsToUser: null,
    description: null,
    returnDate: null,
  };
  constructor(
    private activedRoute: ActivatedRoute,
    private bookService: BookServiceService
  ) {}

  ngOnInit(): void {
    this.activedRoute.params.subscribe((params) => (this.id = params.id));
    console.log(this.id);
    this.bookService.findById(this.id).subscribe((data) => {
      this.book = data;
      console.log(data);
      console.log(this.book);
      console.log(this.book.id);
      if (this.book.returnDate == null) {
        this.available = 'available';
        this.isBorrowable = true;
      }
    });
  }
  borrowBook(): void {
    this.bookService.borrowBook(this.book.id).subscribe(
      (data) => {
        Swal.fire('Success', data.message, 'success');
      },
      (err) => {
        Swal.fire('Error', err.message, 'error');
      }
    );
  }
  returnBook(): void {
    this.bookService.returnBook(this.book.id).subscribe(
      (data) => {
        Swal.fire('Success', 'Book was returned by you', 'success');
      },
      (err) => {
        Swal.fire('Error', err.message, 'error');
      }
    );
  }
}
