import { useMutation, useQueryClient } from "@tanstack/react-query";
import cartService from "../../services/cartService";
import { Cart } from "./useCart";

export const useRemoveCartItem = (username: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: cartService.removeItem,
    onSuccess: (_, bookId) => {
      queryClient.setQueryData<Cart>(["carts", username], (cart) => {
        if (!cart) return cart;
        return {
          ...cart,
          items: cart.items.filter((item) => item.bookId !== bookId),
        };
      });
    },
  });
};
