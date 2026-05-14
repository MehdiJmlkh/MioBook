import { useQuery } from "@tanstack/react-query";
import bookService from "../services/bookService";

export const useBookContent = (id: number) => {
  return useQuery({
    queryKey: ["books", id, "content"],
    queryFn: () => bookService.getBookContent(id),
  });
};
