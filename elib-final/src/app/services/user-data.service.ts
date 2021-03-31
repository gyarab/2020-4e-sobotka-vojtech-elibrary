import { Injectable } from '@angular/core';
const userToken = 'USER_TOKEN';
const userRoles = 'USER_ROLE';
const userName = 'USERNAME';
const userId = 'USER_ID';
@Injectable({
  providedIn: 'root',
})
export class UserDataService {
  constructor() {}
  setUser(token: string, roles: [string], username: string, id: string): void {
    localStorage.clear;
    localStorage.setItem(userToken, token);
    localStorage.setItem(userRoles, JSON.stringify(roles));
    localStorage.setItem(userName, username);
    localStorage.setItem(userId, id);
    console.log('here');
  }
  getToken(): string {
    return localStorage.getItem(userToken);
  }
  getUserName(): string {
    return localStorage.getItem(userName);
  }
  getRole(): string[] {
    return JSON.parse(localStorage.getItem(userRoles));
  }
  getUserId(): string {
    return localStorage.getItem(userId);
  }
  deleteAll(): void {
    window.localStorage.removeItem('USER_TOKEN');
    window.localStorage.removeItem('USER_ROLE');
    window.localStorage.removeItem('USERNAME');
    window.localStorage.removeItem('USER_ID');
  }
}
