import { useMutation, useQueryClient } from "@tanstack/react-query";
import reviewService, { Review } from "../../services/reviewService";

export interface AddReviewRequest {
  title?: string;
  username?: string;
  comment: string;
  rate: number;
}

interface AddReviewError {
  error: string;
}

export const useAddReview = (bookId?: number) => {
  const queryClient = useQueryClient();
  return useMutation<Review, AddReviewError, AddReviewRequest>({
    mutationFn: reviewService.add,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["reviews"],
      });
      queryClient.invalidateQueries({
        queryKey: ["books", bookId],
      });
    },
  });
};
