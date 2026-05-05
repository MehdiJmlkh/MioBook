import apiClient from "./ApiClient";

export interface Review {
  username: string;
  comment: string;
  rate: number;
  date: string;
}

class ReviewService {
  get(bookTitle: string) {
    return apiClient.get(`/reviews/${bookTitle}`).then((res) => res.data);
  }
}

export default new ReviewService();
