import { useMutation, useQueryClient } from "@tanstack/react-query";
import userService, { AddUserRequest, User } from "../services/userService";
import { useNavigate } from "react-router-dom";

interface AddUserError {
  username: string;
  email: string;
}

export const useAddUser = () => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  return useMutation<User, AddUserError, AddUserRequest>({
    mutationFn: userService.create,
    onSuccess: (user) => {
      console.log(user);
      queryClient.setQueryData(["auth"], user);
      console.log(queryClient.getQueryData(["auth"]));
      navigate("/");
    },
  });
};
