import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user.model';
const USER_API = 'http://localhost:8080/api/users/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(
      USER_API + 'signin',
      {
        username,
        password,
      },
      httpOptions
    );
  }

  register(username: string, password: string): Observable<any> {
    return this.http.post(
      USER_API + 'signup',
      {
        username,
        password,
      },
      httpOptions
    );
  }
  changePassword(
    currentPassword: string,
    newPassword: string
  ): Observable<any> {
    return this.http.post(
      USER_API + 'changePassword',
      { currentPassword, newPassword },
      httpOptions
    );
  }
  getAll(): Observable<any> {
    return this.http.get(USER_API);
  }
  deleteUser(username: string): Observable<any> {
    console.log(username);
    return this.http.delete(USER_API + 'deleteUser/' + username);
  }
  setUserAsAdmin(id: string): Observable<any> {
    return this.http.get(USER_API + 'setAsAdmin/' + id);
  }
  searchByUsername(username: string): Observable<any> {
    return this.http.get(USER_API + 'searchByUsername/' + username);
  }
}
