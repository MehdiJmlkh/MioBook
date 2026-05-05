import { useQuery } from "@tanstack/react-query";
import bookService, { Book } from "../services/bookService";

export const useBook = (title: string) => {
  return useQuery<Book>({
    queryKey: ["books", title],
    queryFn: () => bookService.getBook(title),
  });
};
