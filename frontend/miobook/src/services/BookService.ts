import apiClient from "./ApiClient";

class BookService {
  getTopRated() {
    return apiClient.get("/books/top-rated").then((res) => res.data);
  }
}

export default new BookService();
