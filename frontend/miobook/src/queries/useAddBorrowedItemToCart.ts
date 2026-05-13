import { useMutation } from "@tanstack/react-query";
import cartService from "../services/cartService";

export const useAddBorrowedItemToCart = () => {
  return useMutation({
    mutationFn: cartService.addBorrowedItem,
  });
};
