import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { UserService } from '../services/user-service';
const rex = '^[a-zA-Z0-9]+$';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm = new FormGroup({
    username: new FormControl('', [
      Validators.minLength(5),
      Validators.maxLength(20),
      Validators.required,
      Validators.pattern(rex),
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(6),
      Validators.maxLength(20),
    ]),
    repeatedPassword: new FormControl('', [
      Validators.minLength(6),
      Validators.maxLength(20),
      Validators.required,
    ]),
  });
  constructor(private userService: UserService) {}

  ngOnInit(): void {}
  onSubmit(): void {
    if (this.registerForm.controls['username'].invalid) {
      Swal.fire(
        'Error',
        'Username must be long between 5 and 20, also must contain only letters and numbers!',
        'error'
      );
      this.registerForm.controls['username'].setValue('');
      return;
    }

    if (
      this.registerForm.controls['password'].value !=
      this.registerForm.controls['repeatedPassword'].value
    ) {
      Swal.fire('Error', 'Passwords are not matching!', 'error');
      this.registerForm.controls['repeatedPassword'].setValue('');
      this.registerForm.controls['password'].setValue('');
      return;
    }
    if (this.registerForm.controls['password'].invalid) {
      Swal.fire(
        'Error',
        'Passwords must be long between 5 and 20, also must contain only letters and numbers!',
        'error'
      );
    }
    console.log('running');
    this.userService
      .register(
        this.registerForm.controls['username'].value,
        this.registerForm.controls['password'].value
      )
      .subscribe(
        (data) => {
          Swal.fire('Success', data.message, 'success');
        },
        (err) => {
          Swal.fire('error', err.error.message, 'error');
          this.registerForm.controls['repeatedPassword'].setValue('');
          this.registerForm.controls['password'].setValue('');
          this.registerForm.controls['username'].setValue('');
          return;
        }
      );
  }
}
