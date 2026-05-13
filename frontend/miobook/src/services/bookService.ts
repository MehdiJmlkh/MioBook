import { SearchQuery } from "../queries/useFilteredBooks";
import apiClient from "./ApiClient";

export interface Book {
  id: number;
  title: string;
  author: string;
  price: number;
  averageRating: number;
  year: number;
  publisher: string;
  genres: string[];
  synopsis: string;
}

export interface AddBookRequest {
  title: string;
  author: string;
  price: number;
  averageRating: number;
  year: number;
  publisher: string;
  genres: string[];
  synopsis: string;
  content: string;
}

class BookService {
  getBook(title: string) {
    return apiClient.get(`/books/${title}`).then((res) => res.data);
  }

  getBooks() {
    return apiClient.get("books").then((res) => res.data);
  }

  getTopRated() {
    return apiClient.get("/books/top-rated").then((res) => res.data);
  }

  getNewReleases() {
    return apiClient.get("/books/new-releases").then((res) => res.data);
  }

  search(query: SearchQuery) {
    return apiClient
      .get("books/search", { params: query })
      .then((res) => res.data);
  }

  getGenres() {
    return apiClient.get("/books/genres").then((res) => res.data);
  }

  addBook(newBook: AddBookRequest) {
    return apiClient
      .post("/books", { ...newBook, username: "admin1" })
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }
}

export default new BookService();
