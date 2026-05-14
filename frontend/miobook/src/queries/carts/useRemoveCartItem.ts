import { useMutation } from "@tanstack/react-query";
import cartService from "../../services/cartService";

export const useRemoveCartItem = () => {
  return useMutation({
    mutationFn: cartService.removeItem,
  });
};
