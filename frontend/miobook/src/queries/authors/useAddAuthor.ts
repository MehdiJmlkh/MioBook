import { useMutation } from "@tanstack/react-query";
import authorService, {
  AddAuthorRequest,
  Author,
} from "../../services/authorService";

interface addAuthorError {
  name: string;
}

export const useAddAuthor = () => {
  return useMutation<Author, addAuthorError, AddAuthorRequest>({
    mutationFn: authorService.addAuthor,
  });
};
