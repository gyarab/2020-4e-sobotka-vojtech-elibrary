import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Book } from '../model/book.model';
import { BookServiceService } from '../services/book-service.service';

@Component({
  selector: 'app-late-books',
  templateUrl: './late-books.component.html',
  styleUrls: ['./late-books.component.css']
})
export class LateBooksComponent implements OnInit {
  books: Book[];

  constructor(
    private bookService: BookServiceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.bookService.getDelayedBooks().subscribe((data) => {
      this.books = data;
      console.log('here');
      console.log(data[0].returnDate)
    });
  }

  details(id: string): void {
    this.router.navigate(['/bookDetail' + '/' + id]);
  }
}