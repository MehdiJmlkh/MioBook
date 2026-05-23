import { useForm } from "react-hook-form";
import Button from "../Button";
import Input from "../Input";
import Price from "../Price";
import "./Wallet.css";
import { useAddCredit } from "../../queries/credits/useAddCredit";
import { useCredit } from "../../queries/credits/useCredit";
import { useAuth } from "../../queries/auth/useAuth";

interface Props {
  className?: string;
}

interface FormData {
  credit: number | null;
}

const Wallet = ({ className }: Props) => {
  const { register, reset, handleSubmit } = useForm<FormData>();

  const { data: user } = useAuth();

  const { data: balance } = useCredit(user?.username);
  const addCredit = useAddCredit();

  const handleAddCredit = handleSubmit((data) => {
    if (data.credit)
      addCredit.mutate(
        { credit: data.credit * 100, username: user?.username },
        { onSuccess: () => reset({ credit: null }) },
      );
  });

  return (
    <div className={`wallet ${className}`}>
      <Price className="wallet__price">
        {balance}
      </Price>
      <Input
        {...register("credit")}
        type="number"
        placeholder="$Amount"
        className="wallet__input"
        error={addCredit.error?.credit}
      />
      <Button className="btn-primary wallet__btn" onClick={handleAddCredit}>
        Add more credit
      </Button>
    </div>
  );
};

export default Wallet;
