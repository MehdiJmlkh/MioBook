import { useQuery } from "@tanstack/react-query";
import cartService from "../../services/cartService";

interface CartItem {
  bookId: number;
  authorId: number;
  title: string;
  author: string;
  isBorrowed: boolean;
  borrowDays: number;
  price: number;
  finalPrice: number;
}

export interface Cart {
  items: CartItem[];
}

export const useCart = (username: string) => {
  return useQuery<Cart>({
    queryKey: ["carts", username],
    queryFn: () => cartService.getCart(username),
  });
};
