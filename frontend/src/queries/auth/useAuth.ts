import { useQuery } from "@tanstack/react-query";
import authService from "../../services/authService";
import { User } from "../../services/userService";

export const useAuth = () => {
  return useQuery<User>({
    queryKey: ["auth"],
    queryFn: authService.get,
  });
};
