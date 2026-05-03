import { useState } from "react";
import Input from "../Input";
import "./SelectInput.css";
import { FaCaretDown } from "react-icons/fa";

interface Props {
  options?: string[];
  className: string;
  onChange: (option: string) => void;
}

const SelectInput = ({ options = [], className, onChange }: Props) => {
  const [isOpen, setOpen] = useState(false);
  const [value, setValue] = useState("");

  return (
    <div
      className={`select-input ${className}`}
      onClick={() => setOpen(!isOpen)}
    >
      <Input readOnly={true} value={value} className="select-input__input" />
      {isOpen && (
        <ul className="select-input__dropdown">
          {options.map((option) => (
            <li
              key={option}
              className="dropdown__item"
              onClick={() => {
                setValue(option);
                onChange(option);
              }}
            >
              {option}
            </li>
          ))}
        </ul>
      )}
      <FaCaretDown className="select-input__icon" />
    </div>
  );
};

export default SelectInput;
