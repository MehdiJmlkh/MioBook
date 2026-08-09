import { useQuery } from "@tanstack/react-query";
import creditService from "../../services/creditService";

export const useCredit = (username?: string) => {
  return useQuery<number>({
    queryKey: ["credits", username],
    queryFn: () => creditService.get(username),
  });
};
