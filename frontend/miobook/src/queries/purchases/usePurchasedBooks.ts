import { useQuery } from "@tanstack/react-query";
import purchaseService from "../../services/purchaseService";

interface PurchasedBook {
  id: number;
  authorId: number;
  title: string;
  author: string;
  publisher: string;
  genres: string[];
  year: number;
  isBorrowed: boolean;
  expiredDate: string;
}

interface PurchasedBooksHistory {
  books: PurchasedBook[];
}
export const usePurchasedBooks = (username: string) => {
  return useQuery<PurchasedBooksHistory>({
    queryKey: ["purchases", username, "books"],
    queryFn: () => purchaseService.getPurchasedBooks(username),
  });
};
