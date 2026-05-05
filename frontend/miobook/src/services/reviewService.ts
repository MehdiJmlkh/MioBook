import apiClient from "./ApiClient";

class ReviewService {
  get(bookTitle: string) {
    return apiClient.get(`/reviews/${bookTitle}`).then((res) => res.data);
  }
}

export default new ReviewService();
