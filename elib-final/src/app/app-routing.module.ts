import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminUsersBoardComponent } from './admin-users-board/admin-users-board.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BooksComponent } from './books/books.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { CreateBookComponent } from './create-book/create-book.component';
import { CreateGenreOrAuthorComponent } from './create-genre-or-author/create-genre-or-author.component';
import { HomeComponent } from './home/home.component';
import { LateBooksComponent } from './late-books/late-books.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { MyLateBooksComponent } from './my-late-books/my-late-books.component';
import { RegisterComponent } from './register/register.component';
import { UpdateBookComponent } from './update-book/update-book.component';
import { UserBooksComponent } from './user-books/user-books.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'second-component', component: RegisterComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent },
  { path: 'changepassword', component: ChangePasswordComponent },
  { path: 'books', component: BooksComponent },
  { path: 'userbooks', component: UserBooksComponent },
  { path: 'adminUserBoard', component: AdminUsersBoardComponent },
  { path: 'createBook', component: CreateBookComponent },
  { path: 'books', component: BooksComponent },
  { path: 'bookDetail/:id', component: BookDetailComponent },
  { path: 'bookUpdate/:id', component: UpdateBookComponent },
  { path: 'createGenreOrAuthor', component: CreateGenreOrAuthorComponent },
  { path: 'latebooks', component: LateBooksComponent },
  { path: 'mylatebooks', component: MyLateBooksComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
