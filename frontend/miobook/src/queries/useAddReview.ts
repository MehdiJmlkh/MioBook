import { useMutation, useQuery } from "@tanstack/react-query";
import reviewService, { Review } from "../services/reviewService";

export interface AddReviewRequest {
  title: string;
  username: string;
  comment: string;
  rate: number;
}

interface AddReviewError {
  error: string;
}

export const useAddReview = () => {
  return useMutation<Review, AddReviewError, AddReviewRequest>({
    mutationFn: reviewService.add,
  });
};
