import { SetStateAction, useState } from "react";
import { LuCalendarDays } from "react-icons/lu";
import "react-datepicker/dist/react-datepicker.css";

import Input from "../Input";
import DatePicker from "react-datepicker";
import "./DateInput.css";

interface Props {
  placeholder?: string;
  onChange: (date: SetStateAction<Date | null>) => void;
}

const DateInput = ({ placeholder, onChange }: Props) => {
  const [date, setDate] = useState<Date | null>(null);

  return (
    <div className="date-input">
      <LuCalendarDays className="date-input__icon" />
      <DatePicker
        selected={date}
        dateFormat="yyyy-MM-dd"
        onChange={(d: SetStateAction<Date | null>) => {
          setDate(d);
          onChange(d);
        }}
        placeholderText={placeholder}
        customInput={<Input />}
      />
    </div>
  );
};

export default DateInput;
