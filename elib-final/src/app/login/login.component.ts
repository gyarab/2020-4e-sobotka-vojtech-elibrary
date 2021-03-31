import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user-service';
import { UserDataService } from '../services/user-data.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(20),
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(20),
    ]),
  });

  constructor(
    private userService: UserService,
    private userDataService: UserDataService,
    private router: Router
  ) {}
  ngOnInit(): void {
    if (window.localStorage.getItem('USER_TOKEN') !== null) {
      console.log('token is here');
      this.router.navigate['/books'];
    } else {
      console.log('token is not here');
    }
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      Swal.fire(
        'Error',
        'Password or username is too short or too long!',
        'error'
      );
      return;
    }
    this.userService
      .login(
        this.loginForm.controls['username'].value,
        this.loginForm.controls['password'].value
      )
      .subscribe(
        (data) => {
          this.userDataService.setUser(
            data.token,
            data.roles,
            data.username,
            data.id
          );

          Swal.fire('Welcome', 'Login was succesfull', 'success');
          window.location.reload();

          this.loginForm.controls['username'].setValue('');
          this.loginForm.controls['password'].setValue('');
        },
        (err) => {
          console.log(err.status);
          if (err.status == 401) {
            Swal.fire('Error', 'Pasword or username is invalid!', 'error');
          } else {
            Swal.fire('Error', 'Problem with connection to server!', 'error');
          }

          this.loginForm.controls['username'].setValue('');
          this.loginForm.controls['password'].setValue('');
        }
      );
  }
}
