import { PaginationParams } from "../queries/authors/useBooksByAuthor";
import { BookPage } from "../queries/books/useFilteredBooks";
import apiClient from "./apiClient";

export interface Author {
  name: string;
  penName: string;
  nationality: string;
  born?: string;
  died?: string;
  imageLink: string;
}

export interface AddAuthorRequest {
  username?: string;
  name: string;
  penName: string;
  nationality: string;
  born?: string;
  died?: string;
  imageLink: string;
}

class AuthorService {
  getAuthors() {
    return apiClient.get("/authors").then((res) => res.data);
  }

  getAuthor(id: number) {
    return apiClient.get<Author>(`/authors/${id}`).then((res) => res.data);
  }

  getBooksByAuthor(id: number, params: PaginationParams) {
    return apiClient
      .get<BookPage>(`/authors/${id}/books`, { params })
      .then((res) => res.data);
  }

  addAuthor(request: AddAuthorRequest) {
    return apiClient
      .post<Author>("/authors", request)
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }
}

export default new AuthorService();
