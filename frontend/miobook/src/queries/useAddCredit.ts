import { useMutation } from "@tanstack/react-query";
import creditService from "../services/creditService";

interface AddCreditError {
  credit: string;
}

export const useAddCredit = () => {
  return useMutation<any, AddCreditError, number>({
    mutationFn: creditService.addCredit,
  });
};
