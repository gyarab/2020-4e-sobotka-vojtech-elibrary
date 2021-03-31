import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnDestroy, OnInit {
  title = 'elib-final';
  isLoggedIn = false;
  isAdmin = false;
  isUser = false;
  ngOnInit(): void {
    if (window.localStorage.getItem('USER_TOKEN') !== null) {
      this.isLoggedIn = true;
      var roles: string[] = JSON.parse(
        window.localStorage.getItem('USER_ROLE')
      );
      if (roles.indexOf('ROLE_ADMIN') > -1) {
        this.isAdmin = true;
        console.log('admin');
      } else {
        this.isUser = true;
        console.log('user');
      }

      location.reload;
    }
  }
  ngOnDestroy() {
    window.localStorage.removeItem('USER_TOKEN');
    window.localStorage.removeItem('USER_ROLE');
    window.localStorage.removeItem('USERNAME');
    window.localStorage.removeItem('USER_ID');
  }
}
