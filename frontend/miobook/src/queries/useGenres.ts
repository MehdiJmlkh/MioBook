import { useQuery } from "@tanstack/react-query";
import bookService from "../services/bookService";

export const useGenres = () => {
  return useQuery({
    queryKey: ["books", "genres"],
    queryFn: bookService.getGenres,
  });
};
