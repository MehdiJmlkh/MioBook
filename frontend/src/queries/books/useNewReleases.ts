import { useQuery } from "@tanstack/react-query";
import bookService, { Book } from "../../services/bookService";

export const useNewReleases = () => {
  return useQuery<Book[]>({
    queryKey: ["books", "new-releases"],
    queryFn: bookService.getNewReleases,
  });
};
