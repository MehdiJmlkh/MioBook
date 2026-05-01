import { SetStateAction, useState } from "react";
import { LuCalendarDays } from "react-icons/lu";
import Input from "../Input";
import DatePicker from "react-datepicker";
import "./DateInput.css";

interface Props {
  placeholder?: string;
}

export default function DateInput({ placeholder }: Props) {
  const [date, setDate] = useState<Date | null>(null);

  return (
    <div className="date-input">
      <LuCalendarDays className="date-input__icon" />
      <DatePicker
        selected={date}
        onChange={(d: SetStateAction<Date | null>) => setDate(d)}
        placeholderText={placeholder}
        customInput={<Input />}
      />
    </div>
  );
}
