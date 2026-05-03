import { useQuery } from "@tanstack/react-query";
import bookService, { Book } from "../services/bookService";

export interface SearchQuery {
  title?: string;
  author?: string;
  genre?: string;
  year?: number;
  sortBy: "Rating" | "Reviews";
  order?: "Descending" | "Ascending";
  page: number;
  size: number;
}

export const useBooks = (
  query: SearchQuery,
) => {
  return useQuery<Book[]>({
    queryKey: ["books", "search", query],
    queryFn: () => bookService.search(query),
  });
};
