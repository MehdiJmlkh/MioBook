import { useMutation, useQueryClient } from "@tanstack/react-query";
import authService, { LoginRequest } from "../services/authService";
import { useNavigate } from "react-router-dom";
import { User } from "../services/userService";

interface LoginRequestError {
  error: string;
}

export const useLogin = () => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  return useMutation<User, LoginRequestError, LoginRequest>({
    mutationFn: authService.login,
    onSuccess: (user) => {
      queryClient.setQueryData(["auth"], user);
      navigate("/");
    },
  });
};
