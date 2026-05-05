import { useQuery } from "@tanstack/react-query";
import reviewService from "../services/reviewService";

export interface Review {
  username: string;
  comment: string;
  rate: number;
}

interface ReviewList {
  reviews: Review[];
}

export const useReviews = (bookTitle: string) => {
  return useQuery<ReviewList>({
    queryKey: ["reviews", bookTitle],
    queryFn: () => reviewService.get(bookTitle),
  });
};
