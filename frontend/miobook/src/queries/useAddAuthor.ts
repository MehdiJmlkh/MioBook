import { useMutation } from "@tanstack/react-query";
import authService from "../services/authService";
import authorService, { Author } from "../services/authorService";

interface addAuthorError {
  name: string;
}

export const useAddAuthor = () => {
  return useMutation<Author, addAuthorError, Author>({
    mutationFn: authorService.addAuthor,
  });
};
