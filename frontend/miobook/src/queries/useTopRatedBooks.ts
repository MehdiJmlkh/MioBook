import { useQuery } from "@tanstack/react-query";
import bookService, { Book } from "../services/bookService";

export const useTopRatedBooks = () => {
  return useQuery<Book[]>({
    queryKey: ["books", "top-rated"],
    queryFn: bookService.getTopRated,
  });
};
