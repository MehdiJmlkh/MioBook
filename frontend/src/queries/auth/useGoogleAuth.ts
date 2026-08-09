import { useMutation } from "@tanstack/react-query";
import authService, { LoginResponse } from "../../services/authService";
import { useNavigate } from "react-router-dom";

export const useGoogleAuth = () => {
  const navigate = useNavigate();

  return useMutation({
    mutationFn: authService.googleAuth,
    onSuccess: (response: LoginResponse) => {
      localStorage.setItem("accessToken", response.token);
      navigate("/");
    },
  });
};
