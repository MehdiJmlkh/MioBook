import { useMutation, useQueryClient } from "@tanstack/react-query";
import authService, { LoginRequest, LoginResponse } from "../../services/authService";
import { useNavigate } from "react-router-dom";
import { User } from "../../services/userService";

interface LoginRequestError {
  error: string;
}

export const useLogin = () => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  return useMutation<LoginResponse, LoginRequestError, LoginRequest>({
    mutationFn: authService.login,
    onSuccess: (response: LoginResponse) => {
      localStorage.setItem("token", response.token);
      navigate("/");
    },
  });
};
