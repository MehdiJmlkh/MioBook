import { useMutation } from "@tanstack/react-query";
import creditService from "../services/creditService";

interface AddCreditError {
  credit: string;
}

interface Props {
  onSuccess?: () => void;
}

export const useAddCredit = ({ onSuccess }: Props) => {
  return useMutation<any, AddCreditError, number>({
    mutationFn: creditService.addCredit,
    onSuccess,
  });
};
