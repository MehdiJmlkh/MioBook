import { useMutation } from "@tanstack/react-query";
import authService from "../services/authService";
import authorService, { Author } from "../services/authorService";

interface addAuthorError {
  name: string;
}

interface Props {
  onSuccess?: (author: Author) => void;
}

export const useAddAuthor = ({ onSuccess }: Props) => {
  return useMutation<Author, addAuthorError, Author>({
    mutationFn: authorService.addAuthor,
    onSuccess,
  });
};
