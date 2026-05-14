import { useMutation } from "@tanstack/react-query";
import userService, { AddUserRequest, User } from "../../services/userService";
import { useLogin } from "../useLogin";

interface AddUserError {
  username: string;
  email: string;
}

export const useAddUser = () => {
  const login = useLogin();

  return useMutation<User, AddUserError, AddUserRequest>({
    mutationFn: userService.create,
    onSuccess: (user, newUser) => {
      login.mutate({ username: newUser.username, password: newUser.password });
    },
  });
};
