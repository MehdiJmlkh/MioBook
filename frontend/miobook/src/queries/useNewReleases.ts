import { useQuery } from "@tanstack/react-query";
import { Book } from "./useTopRatedBooks";
import BookService from "../services/BookService";

export const useNewReleases = () => {
  return useQuery<Book[]>({
    queryKey: ["books", "new-releases"],
    queryFn: BookService.getNewReleases,
  });
};
