import { useMutation } from "@tanstack/react-query";
import cartService from "../../services/cartService";
import { toast } from "react-toastify";

export const useAddItemToCart = () => {
  return useMutation({
    mutationFn: cartService.addItem,
    onSuccess: () => {
      toast.success("Book added to cart");
    },
    onError: (err: Error) => {
      toast.error(err.message);
    },
  });
};
