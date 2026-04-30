import Button from "../Button";
import Input from "../Input";
import Price from "../Price";
import "./Wallet.css";

const Wallet = () => {
  return (
    <div className="wallet">
      <Price className="wallet__price">1,000</Price>
      <Input type="number" placeholder="$Amount"/>
      <Button className="btn-primary wallet__btn">Add more credit</Button>
    </div>
  );
};

export default Wallet;
