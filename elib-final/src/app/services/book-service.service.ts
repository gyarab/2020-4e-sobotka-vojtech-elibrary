import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { Book } from '../model/book.model';
const API = 'http://localhost:8080/api/books';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class BookServiceService {
  constructor(private http: HttpClient) {}
  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(API);
  }
  findByTitle(search: string): Observable<Book[]> {
    return this.http.get<Book[]>(API + '/find/' + search);
  }
  findById(id: string): Observable<Book> {
    return this.http.get<Book>(API + '/' + id);
  }
  deleteById(id: string): Observable<any> {
    console.log('delete');
    return this.http.delete(API + '/deleteById/' + id);
  }
  createBook(
    title: string,
    description: string,
    authors: string[],
    genres: string[],
    dateOfPublish: number
  ): Observable<any> {
    return this.http.post(
      API,
      { title, description, authors, genres, dateOfPublish },
      httpOptions
    );
  }
  updateBook(
    id: string,
    title?: string,
    description?: string,
    authors?: string[],
    genres?: string[],
    dateOfPublish?: number
  ): Observable<any> {
    return this.http.put(
      API + '/' + id,
      { title, description, authors, genres, dateOfPublish },
      httpOptions
    );
  }
  borrowBook(id: string): Observable<any> {
    return this.http.get(API + '/borrowBook/' + id);
  }
  returnBook(id: string): Observable<any> {
    return this.http.get(API + '/returnBook/' + id);
  }
  filterBooks(authors: string[], genres: string[]): Observable<any> {
    return this.http.post(
      API + '/findByParameters',
      { authors, genres },
      httpOptions
    );
  }
  getUserBooks(): Observable<any> {
    return this.http.get(API + '/userBooks');
  }
  getDelayedBooks(): Observable<any> {
    return this.http.get(API + '/checkForDelayed');
  }
  getMyDelayedBooks(): Observable<any> {
    return this.http.get(API + '/myLateBooks');
  }
}
