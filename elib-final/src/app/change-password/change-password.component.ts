import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { UserService } from '../services/user-service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  constructor(private userService: UserService) {}
  loginForm = new FormGroup({
    currentPassword: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(20),
    ]),
    newPassword: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(20),
    ]),
  });
  ngOnInit(): void {
    console.log('works');
  }
  onSubmit(): void {
    if (this.loginForm.controls['currentPassword'].invalid) {
      Swal.fire('Error', 'Password must be long between 5 and 20!', 'error');
    }
    if (this.loginForm.controls['newPassword'].invalid) {
      Swal.fire(
        'Error',
        'New password must be long between 5 and 20!',
        'error'
      );
    }
    this.userService
      .changePassword(
        this.loginForm.controls['currentPassword'].value,
        this.loginForm.controls['newPassword'].value
      )
      .subscribe(
        (data) => {
          Swal.fire('Succes', data.message, 'success');
        },
        (err) => {
          if (err.status == 418) {
            Swal.fire('Error', 'Current password is not correct', 'error');
          }

          Swal.fire('Error','Current password is not correct', 'error');
        }
      );
  }
}