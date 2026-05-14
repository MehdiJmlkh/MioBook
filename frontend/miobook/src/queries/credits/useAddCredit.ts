import { useMutation, useQueryClient } from "@tanstack/react-query";
import creditService, { AddCreditRequest } from "../../services/creditService";

interface AddCreditError {
  credit: string;
}

export const useAddCredit = () => {
  const queryClient = useQueryClient();
  return useMutation<any, AddCreditError, AddCreditRequest>({
    mutationFn: creditService.addCredit,
    onSuccess: (newBalance, request) => {
      queryClient.setQueryData(["credits", request.username], newBalance);
    },
  });
};
