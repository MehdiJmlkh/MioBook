import { useQuery } from "@tanstack/react-query";
import reviewService, { Review } from "../services/reviewService";

interface ReviewList {
  reviews: Review[];
}

export const useReviews = (bookTitle: string) => {
  return useQuery<ReviewList>({
    queryKey: ["reviews", bookTitle],
    queryFn: () => reviewService.get(bookTitle),
  });
};
