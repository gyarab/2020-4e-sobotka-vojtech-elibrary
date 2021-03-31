export class Book {
  id?: string;
  title?: string;
  description?: string;
  dateOfPublish?: number;
  authors: string[];
  genres: string[];
  belongsToUser: string;
  returnDate: string;
}
