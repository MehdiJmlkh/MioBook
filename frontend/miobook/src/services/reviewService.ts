import { AddReviewRequest } from "../queries/reviews/useAddReview";
import { PageParams } from "../queries/reviews/useReviews";
import apiClient from "./ApiClient";

export interface Review {
  username: string;
  comment: string;
  rate: number;
  date: string;
}

class ReviewService {
  get(bookId: number, params: PageParams) {
    return apiClient
      .get(`/reviews/${bookId}`, { params })
      .then((res) => res.data);
  }

  add(request: AddReviewRequest) {
    return apiClient
      .post("/reviews", request)
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }
}

export default new ReviewService();
