import { useMutation } from "@tanstack/react-query";
import cartService from "../services/cartService";

export const useAddItemToCart = () => {
  return useMutation({
    mutationFn: cartService.addItem,
  });
};
