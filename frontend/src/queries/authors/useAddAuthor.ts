import { useMutation, useQueryClient } from "@tanstack/react-query";
import authorService, {
  AddAuthorRequest,
  Author,
} from "../../services/authorService";

interface addAuthorError {
  name: string;
  error: string;
}

export const useAddAuthor = () => {
  const queryClient = useQueryClient();
  return useMutation<Author, addAuthorError, AddAuthorRequest>({
    mutationFn: authorService.addAuthor,
    onSuccess: (savedAuthor) => {
      queryClient.setQueryData<Author[]>(["authors"], (oldAuthors) => {
        return [...(oldAuthors || []), savedAuthor];
      });
    },
  });
};
