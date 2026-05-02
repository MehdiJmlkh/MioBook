import "./Price.css";

interface Props {
  children: string | number;
  className?: string;
}

const Price = ({ children, className }: Props) => {
  return <span className={className}>${children}</span>;
};

export default Price;
