import { useQuery } from "@tanstack/react-query";
import BookService, { Book } from "../services/BookService";

export const useNewReleases = () => {
  return useQuery<Book[]>({
    queryKey: ["books", "new-releases"],
    queryFn: BookService.getNewReleases,
  });
};
