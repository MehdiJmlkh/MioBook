import { useQuery } from "@tanstack/react-query";
import authService from "../services/authService";

interface User {
  username: string;
  email: string;
  role: "CUSTOMER" | "ADMIN";
}

export const useAuth = () => {
  return useQuery<User>({
    queryKey: ["auth"],
    queryFn: authService.get,
  });
};
