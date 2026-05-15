import { useMutation } from "@tanstack/react-query";
import bookService, { AddBookRequest, Book } from "../../services/bookService";

interface addBookError {
  title: string;
  author: string;
}


export const useAddBook = () => {
  return useMutation<Book, addBookError, AddBookRequest>({
    mutationFn: bookService.addBook,
  });
};
