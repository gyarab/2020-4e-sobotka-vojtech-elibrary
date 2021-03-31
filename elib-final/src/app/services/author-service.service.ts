import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Author } from '../model/author.model';
import { Genre } from '../model/genre.model';
import { Observable } from 'rxjs';
const API = 'http://localhost:8080/api/authors';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};
@Injectable({
  providedIn: 'root',
})
export class AuthorServiceService {
  constructor(private http: HttpClient) {}
  getAllAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>(API);
  }
  createAuthor(name: string): Observable<any> {
    return this.http.post(API, { name }, httpOptions);
  }
}
