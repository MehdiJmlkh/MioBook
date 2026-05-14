import { useMutation } from "@tanstack/react-query";
import cartService from "../../services/cartService";

export const usePurchaseCart = () => {
  return useMutation<any, Error, string>({
    mutationFn: cartService.purchase,
  });
};
