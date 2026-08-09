import { useQuery } from "@tanstack/react-query";
import bookService, { Book } from "../../services/bookService";

export const useBooks = () => {
  return useQuery<Book[]>({
    queryKey: ["books"],
    queryFn: bookService.getBooks,
  });
};
