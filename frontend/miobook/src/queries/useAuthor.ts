import { useQuery } from "@tanstack/react-query";
import authorService from "../services/authorService";

export const useAuthor = (id: number) => {
  return useQuery({
    queryKey: ["authors", id],
    queryFn: () => authorService.getAuthor(id),
  });
};
