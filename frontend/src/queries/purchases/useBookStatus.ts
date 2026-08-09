import { useQuery } from "@tanstack/react-query";
import purchaseService from "../../services/purchaseService";

export enum BookStatus {
  Owned = "Owned",
  Borrowed = "Borrowed",
  Available = "Available",
}

export const useBookStatus = (username?: string, bookId?: number) => {
  return useQuery<BookStatus>({
    queryKey: ["purchases", username, "books", bookId],
    queryFn: () => purchaseService.getPurchasedBookStatus(username, bookId),
  });
};
