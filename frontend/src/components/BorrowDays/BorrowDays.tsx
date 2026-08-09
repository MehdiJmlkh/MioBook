import RadioInput from "../RadioInput";
import "./BorrowDays.css";

interface Props {
  className?: string;
  onSelectDay: (days: number) => void;
  selectedDay: number;
}

const BorrowDays = ({ className, onSelectDay, selectedDay }: Props) => {
  return (
    <div className={`borrow-days ${className}`}>
      <RadioInput
        key={1}
        name="borrow-days"
        value="1 Day"
        checked={selectedDay === 1}
        onClick={() => onSelectDay(1)}
      />
      {Array.from({ length: 8 }).map((_, i) => (
        <RadioInput
          key={i + 2}
          name="borrow-days"
          value={`${i + 2} Days`}
          checked={selectedDay === i + 2}
          onClick={() => onSelectDay(i + 2)}
        />
      ))}
    </div>
  );
};

export default BorrowDays;
