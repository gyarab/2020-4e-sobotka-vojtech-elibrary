import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Genre } from '../model/genre.model';
import { Observable } from 'rxjs';
const API = 'http://localhost:8080/api/genres';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};
@Injectable({
  providedIn: 'root',
})
export class GenreServiceService {
  constructor(private http: HttpClient) {}
  getAllGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(API);
  }
  createGenre(name: string): Observable<any> {
    return this.http.post(API, { name }, httpOptions);
  }
}
