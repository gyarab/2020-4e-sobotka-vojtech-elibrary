import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { UserDataService } from '../services/user-data.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css'],
})
export class LogoutComponent implements OnInit {
  constructor(
    private userDataService: UserDataService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (window.localStorage.getItem('USER_TOKEN') === null) {
      console.log('true');
      return;
    }
    /* window.localStorage.removeItem('USER_TOKEN');
    window.localStorage.removeItem('USER_ROLE');
    window.localStorage.removeItem('USERNAME');
    window.localStorage.removeItem('USER_ID');
    console.log('here');
    window.location.reload;*/
    Swal.fire({
      title: 'Do you really want to logout?',
      text: 'Your access token will be deleted!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, logout me!',
    }).then((result) => {
      if (result.isConfirmed) {
        window.localStorage.removeItem('USER_TOKEN');
        window.localStorage.removeItem('USER_ROLE');
        window.localStorage.removeItem('USERNAME');
        window.localStorage.removeItem('USER_ID');
        window.location.reload();
      }
    });
  }
}
