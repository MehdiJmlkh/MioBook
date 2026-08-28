import { useQuery } from "@tanstack/react-query";
import purchaseService from "../../services/purchaseService";

interface PurchaseItem {
  bookId: number;
  authorId: number;
  title: string;
  author: string;
  imageLink: string;
  isBorrowed: boolean;
  borrowDays: number;
  price: number;
  finalPrice: number;
}

interface Purchase {
  purchaseDate: string;
  totalCost: number;
  items: PurchaseItem[];
}

interface PurchaseHistory {
  purchaseHistory: Purchase[];
}

export const usePurchases = (username: string) => {
  return useQuery<PurchaseHistory>({
    queryKey: ["purchases", username],
    queryFn: () => purchaseService.getAll(username),
  });
};
