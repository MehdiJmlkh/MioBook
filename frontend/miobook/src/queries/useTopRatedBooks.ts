import { useQuery } from "@tanstack/react-query";
import bookService  from "../services/BookService";

export interface Book {
  title: string;
  author: string;
  price: number;
  averageRating: number;
}

export const useTopRatedBooks = () => {
  return useQuery<Book[]>({
    queryKey: ["books", "top-rated"],
    queryFn: bookService.getTopRated,
  });
};
