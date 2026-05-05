import { useForm } from "react-hook-form";
import Button from "../Button";
import Input from "../Input";
import Price from "../Price";
import "./Wallet.css";
import { useAddCredit } from "../../queries/useAddCredit";
import { useCredit } from "../../queries/useCredit";

interface Props {
  className?: string;
}

interface FormData {
  credit: number | null;
}

const Wallet = ({ className }: Props) => {
  const { register, reset, handleSubmit } = useForm<FormData>();

  const onSuccess = () => {
    reset({ credit: null });
  };

  const { data: balance } = useCredit("li_wei");
  const addCredit = useAddCredit({ onSuccess });

  return (
    <div className={`wallet ${className}`}>
      <Price className="wallet__price">{balance?.toLocaleString() || "0"}</Price>
      <Input
        {...register("credit")}
        type="number"
        placeholder="$Amount"
        className="wallet__input"
        error={addCredit.error?.credit}
      />
      <Button
        className="btn-primary wallet__btn"
        onClick={handleSubmit((data) => {
          if (data.credit) addCredit.mutate(data.credit);
        })}
      >
        Add more credit
      </Button>
    </div>
  );
};

export default Wallet;
