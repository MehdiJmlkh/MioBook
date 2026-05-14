import { useMutation } from "@tanstack/react-query";
import bookService, { AddBookRequest, Book } from "../../services/bookService";

interface addBookError {
  title: string;
  author: string;
}

interface Props {
  onSuccess?: (book: Book) => void;
}

export const useAddBook = ({ onSuccess }: Props) => {
  return useMutation<Book, addBookError, AddBookRequest>({
    mutationFn: bookService.addBook,
    onSuccess,
  });
};
