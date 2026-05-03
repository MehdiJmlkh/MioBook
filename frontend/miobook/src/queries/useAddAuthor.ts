import { useMutation } from "@tanstack/react-query";
import authService from "../services/authService";
import authorService from "../services/authorService";

export const useAddAuthor = () => {
  return useMutation({
    mutationFn: authorService.addAuthor,
  });
};
