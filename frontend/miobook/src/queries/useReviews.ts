import { useQuery } from "@tanstack/react-query";
import reviewService, { Review } from "../services/reviewService";

interface ReviewList {
  reviews: Review[];
  totalReviews: number;
}

export interface PageParams {
  page: number;
  size: number;
}

export const useReviews = (bookTitle: string, params: PageParams) => {
  return useQuery<ReviewList>({
    queryKey: ["reviews", bookTitle, params],
    queryFn: () => reviewService.get(bookTitle, params),
  });
};
