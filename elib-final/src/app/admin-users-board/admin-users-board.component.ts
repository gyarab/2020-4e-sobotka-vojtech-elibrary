import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import Swal from 'sweetalert2';
import { User } from '../model/user.model';
import { UserService } from '../services/user-service';

@Component({
  selector: 'app-admin-users-board',
  templateUrl: './admin-users-board.component.html',
  styleUrls: ['./admin-users-board.component.css'],
})
export class AdminUsersBoardComponent implements OnInit {
  users: User[] = [];
  searchForm = this.formBuilder.group({ input: '' });
  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }
  loadUsers(): void {
    this.userService.getAll().subscribe((data) => {
      for (let index = 0; index < data.length; index++) {
        var user = { id: null, username: null, password: null, roles: null };
        user.id = data[index].id;
        user.username = data[index].username;
        user.password = data[index].password;
        user.roles = data[index].roles;
        this.users.push(user);
      }
    });
  }
  deleteUser(username: string): void {
    Swal.fire({
      title: 'Warning?',
      text: 'Do you really want to delete this innocent user?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Delete!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.deleteUser(username).subscribe(
          (data) => {
            console.log(data);
          },
          (err) => {
            Swal.fire('Error', 'User got unreturned books', 'error');
          }
        );
      }
    });
    window.location.reload;
  }
  setAdmin(id: string): void {
    Swal.fire({
      title: 'Warning?',
      text: 'Do you really want to set this user as admin?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Do it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.setUserAsAdmin(id).subscribe((data) => {});
      }
    });
    window.location.reload;
  }
  search() {
    if (this.searchForm.controls['input'].value === '') {
      return;
    }
    this.userService
      .searchByUsername(this.searchForm.controls['input'].value)
      .subscribe((data) => {
        this.users = data;
      });
  }
}
