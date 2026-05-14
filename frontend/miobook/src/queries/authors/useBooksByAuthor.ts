import { useQuery } from "@tanstack/react-query";
import authorService from "../../services/authorService";

export interface PaginationParams {
  page: number;
  size: number;
}

export const useBooksByAuthor = (id: number, params: PaginationParams) => {
  return useQuery({
    queryKey: ["authors", id, "books", params],
    queryFn: () => authorService.getBooksByAuthor(id, params),
  });
};
