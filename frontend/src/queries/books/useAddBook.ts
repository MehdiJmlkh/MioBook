import { useMutation, useQueryClient } from "@tanstack/react-query";
import bookService, { AddBookRequest, Book } from "../../services/bookService";

interface addBookError {
  title: string;
  author: string;
  error: string;
}

export const useAddBook = () => {
  const queryClient = useQueryClient();
  return useMutation<Book, addBookError, AddBookRequest>({
    mutationFn: bookService.addBook,
    onSuccess: (savedBook) => {
      queryClient.setQueryData<Book[]>(["books"], (books) => [
        ...(books || []),
        savedBook,
      ]);
    },
  });
};
