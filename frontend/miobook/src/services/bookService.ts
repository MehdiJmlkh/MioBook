import { SearchQuery } from "../queries/useFilteredBooks";
import apiClient from "./ApiClient";

export interface Book {
  title: string;
  author: string;
  price: number;
  averageRating: number;
}

class BookService {
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
}

export default new BookService();
