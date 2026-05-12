import { useMutation, useQueryClient } from "@tanstack/react-query";
import authService from "../services/authService";

export const useLogout = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: authService.logout,
    onSuccess: () => {
      queryClient.setQueryData(["auth"], null);
    },
  });
};
