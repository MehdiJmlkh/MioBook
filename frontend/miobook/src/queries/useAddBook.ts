import { useMutation } from "@tanstack/react-query";
import bookService, { Book } from "../services/bookService";
import { AddBookRequest } from "../components/AddBookModal/AddBookModal";

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
