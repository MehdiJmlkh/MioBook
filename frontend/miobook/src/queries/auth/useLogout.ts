import { useMutation, useQueryClient } from "@tanstack/react-query";
import authService from "../../services/authService";
import { useNavigate } from "react-router-dom";

export const useLogout = () => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  return useMutation({
    mutationFn: authService.logout,
    onMutate: () => {
      navigate("/sign-in");
    },
    onSuccess: () => {
      queryClient.setQueryData(["auth"], null);
    },
  });
};
