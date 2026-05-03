import { useQuery } from "@tanstack/react-query";
import authorService, { Author } from "../services/authorService";

export const useAuthors = () => {
  return useQuery<Author[]>({
    queryKey: ["authors"],
    queryFn: authorService.getAuthors,
  });
};
