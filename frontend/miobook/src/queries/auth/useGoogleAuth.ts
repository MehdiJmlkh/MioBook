import { useMutation } from "@tanstack/react-query";
import authService from "../../services/authService";

export const useGoogleAuth = () => useMutation({
  mutationFn: authService.googleAuth,
});
