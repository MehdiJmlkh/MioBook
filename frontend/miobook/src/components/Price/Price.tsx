import "./Price.css";

interface Props {
  children?: string | number;
  className?: string;
}

const Price = ({ children = 0, className }: Props) => {
  const priceInCent = parseInt(children.toString());
  const priceInDollar = priceInCent / 100;

  return <span className={className}>${priceInDollar.toLocaleString()}</span>;
};

export default Price;
