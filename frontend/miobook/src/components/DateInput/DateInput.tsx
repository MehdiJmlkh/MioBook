import { format } from "date-fns";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { Control, Controller, FieldValues, Path } from "react-hook-form";
import { LuCalendarDays } from "react-icons/lu";
import Input from "../Input";
import "./DateInput.css";

interface Props<T extends FieldValues> {
  placeholder?: string;
  control: Control<T>;
  name: Path<T>;
}

const DateInput = <T extends FieldValues>({
  placeholder,
  control,
  name,
}: Props<T>) => {
  return (
    <div className="date-input">
      <LuCalendarDays className="date-input__icon" />
      <Controller
        control={control}
        name={name}
        render={({ field }) => (
          <DatePicker
            onKeyDown={(e) => e.preventDefault()}
            selected={field.value ? new Date(field.value) : null}
            onChange={(date: Date | null) => {
              if (date) {
                const formatted = format(date, "yyyy-MM-dd");
                field.onChange(formatted);
              } else {
                field.onChange("");
              }
            }}
            dateFormat="yyyy-MM-dd"
            placeholderText={placeholder}
            customInput={<Input/>}
          />
        )}
      />
    </div>
  );
};

export default DateInput;
