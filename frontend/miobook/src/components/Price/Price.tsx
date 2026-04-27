import "./Price.css";

interface Props {
  children: string;
  className?: string;
}

const Price = ({ children, className }: Props) => {
  return <span className={className}>${children}</span>;
};

export default Price;
