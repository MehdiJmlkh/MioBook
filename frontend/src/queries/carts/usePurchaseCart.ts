import { useMutation, useQueryClient } from "@tanstack/react-query";
import cartService from "../../services/cartService";
import { toast } from "react-toastify";

export const usePurchaseCart = () => {
  const queryClient = useQueryClient();

  return useMutation<any, Error, string>({
    mutationFn: cartService.purchase,
    onSuccess: (_, username) => {
      toast.success("Purchase completed.");
      queryClient.removeQueries({ queryKey: ["carts", username] });
    },
    onError: (err) => {
      toast.error(err.message);
    },
  });
};
