import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { LogoutComponent } from './logout/logout.component';
import { UserBooksComponent } from './user-books/user-books.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { CreateGenreOrAuthorComponent } from './create-genre-or-author/create-genre-or-author.component';
import { AdminUsersBoardComponent } from './admin-users-board/admin-users-board.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BooksComponent } from './books/books.component';
import { HomeComponent } from './home/home.component';
import { CreateBookComponent } from './create-book/create-book.component';
import { authInterceptorProviders } from './interceptors/auth.interceptor';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { LateBooksComponent } from './late-books/late-books.component';
import { UpdateBookComponent } from './update-book/update-book.component';
import { MyLateBooksComponent } from './my-late-books/my-late-books.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent,
    UserBooksComponent,
    ChangePasswordComponent,
    CreateGenreOrAuthorComponent,
    AdminUsersBoardComponent,
    BookDetailComponent,
    BooksComponent,
    HomeComponent,
    CreateBookComponent,
    LateBooksComponent,
    UpdateBookComponent,
    MyLateBooksComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgMultiSelectDropDownModule.forRoot(),
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
