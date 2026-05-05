import { useForm } from "react-hook-form";
import Button from "../Button";
import Input from "../Input";
import Price from "../Price";
import "./Wallet.css";
import { useAddCredit } from "../../queries/useAddCredit";

interface Props {
  className?: string;
}

interface FormData {
  credit: number;
}

const Wallet = ({ className }: Props) => {
  const { register, reset, handleSubmit } = useForm<FormData>();

  const onSuccess = () => {
    reset();
  };

  const addCredit = useAddCredit({ onSuccess });

  return (
    <div className={`wallet ${className}`}>
      <Price className="wallet__price">1,000</Price>
      <Input
        {...register("credit")}
        type="number"
        placeholder="$Amount"
        className="wallet__input"
        error={addCredit.error?.credit}
      />
      <Button
        className="btn-primary wallet__btn"
        onClick={handleSubmit((data) => addCredit.mutate(data.credit))}
      >
        Add more credit
      </Button>
    </div>
  );
};

export default Wallet;
