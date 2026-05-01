import Button from "../Button";
import Input from "../Input";
import Price from "../Price";
import "./Wallet.css";

interface Props {
  className?: string;
}

const Wallet = ({ className }: Props) => {
  return (
    <div className={`wallet ${className}`}>
      <Price className="wallet__price">1,000</Price>
      <Input type="number" placeholder="$Amount" />
      <Button className="btn-primary wallet__btn">Add more credit</Button>
    </div>
  );
};

export default Wallet;
