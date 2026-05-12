import RadioInput from "../RadioInput";
import "./BorrowDays.css";

interface Props {
  className?: string;
  onSelectDay: (days: number) => void;
}

const BorrowDays = ({ className, onSelectDay }: Props) => {
  return (
    <div className={`borrow-days ${className}`}>
      <RadioInput
        key={1}
        name="borrow-days"
        value="1 Day"
        defaultChecked={true}
        onClick={() => onSelectDay(1)}
      />
      {Array.from({ length: 8 }).map((_, i) => (
        <RadioInput
          key={i + 2}
          name="borrow-days"
          value={`${i + 2} Days`}
          onClick={() => onSelectDay(i + 2)}
        />
      ))}
    </div>
  );
};

export default BorrowDays;
