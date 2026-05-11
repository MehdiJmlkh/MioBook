import { useMutation } from "@tanstack/react-query";
import authService, { LoginRequest } from "../services/authService";

interface LoginRequestError {
  error: string;
}

export const useLogin = () => {
  return useMutation<unknown, LoginRequestError, LoginRequest>({
    mutationFn: authService.login,
  });
};
