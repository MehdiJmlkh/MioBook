import RadioInput from "../RadioInput";
import "./BorrowDays.css";

interface Props {
  className?: string;
}

const BorrowDays = ({ className }: Props) => {
  return (
    <div className={`borrow-days ${className}`}>
      <RadioInput
        key={1}
        name="borrow-days"
        value="1 Day"
        defaultChecked={true}
      />
      {Array.from({ length: 8 }).map((_, i) => (
        <RadioInput key={i + 2} name="borrow-days" value={`${i + 2} Days`} />
      ))}
    </div>
  );
};

export default BorrowDays;
