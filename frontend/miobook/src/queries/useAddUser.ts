import { useMutation } from "@tanstack/react-query";
import userService, { User } from "../services/userService";

interface AddUserError {
  username: string;
  email: string;
}

export const useAddUser = () => {
  return useMutation<User, AddUserError, User>({
    mutationFn: userService.create,
  });
};
