import { useQuery } from "@tanstack/react-query";
import bookService, { Book } from "../services/bookService";

export const useBook = (id: number) => {
  return useQuery<Book>({
    queryKey: ["books", id],
    queryFn: () => bookService.getBook(id),
  });
};
