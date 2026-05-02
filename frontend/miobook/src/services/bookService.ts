import apiClient from "./ApiClient";

export interface Book {
  title: string;
  author: string;
  price: number;
  averageRating: number;
}

class BookService {
  getTopRated() {
    return apiClient.get("/books/top-rated").then((res) => res.data);
  }

  getNewReleases() {
    return apiClient.get("/books/new-releases").then((res) => res.data);
  }
}

export default new BookService();
