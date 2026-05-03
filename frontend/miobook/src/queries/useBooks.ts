import { useQuery } from "@tanstack/react-query";
import bookService, { Book } from "../services/bookService";

export interface SearchQuery {
  title?: string;
  author?: string;
  genre?: string;
  year?: number;
  sortBy: "Rating" | "Reviews";
  descending?: boolean;
}

export const useBooks = (query: SearchQuery, params: Record<string, number>) => {
  return useQuery<Book[]>({
    queryKey: ["books", "search", query, params],
    queryFn: () => bookService.search(query, params),
  });
};
