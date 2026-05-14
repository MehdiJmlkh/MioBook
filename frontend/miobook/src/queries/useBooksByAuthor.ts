import { useQuery } from "@tanstack/react-query";
import authorService from "../services/authorService";

export const useBooksByAuthor = (id: number) => {
  return useQuery({
    queryKey: ["authors", id, "books"],
    queryFn: () => authorService.getBooksByAuthor(id),
  });
};
