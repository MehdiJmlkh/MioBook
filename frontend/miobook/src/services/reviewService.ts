import { PageParams } from "../queries/useReviews";
import apiClient from "./ApiClient";

export interface Review {
  username: string;
  comment: string;
  rate: number;
  date: string;
}

class ReviewService {
  get(bookTitle: string, params: PageParams) {
    return apiClient.get(`/reviews/${bookTitle}`, {params}).then((res) => res.data);
  }
}

export default new ReviewService();
