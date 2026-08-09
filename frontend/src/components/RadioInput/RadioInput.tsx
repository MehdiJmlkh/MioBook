import "./RadioInput.css";
import { LuBriefcase } from "react-icons/lu";
import { HiOutlineUserCircle } from "react-icons/hi2";
import React from "react";

export enum Role {
  Customer = "customer",
  Manager = "manager",
}

interface Props extends React.InputHTMLAttributes<HTMLInputElement> {
  name: string;
  value: string;
  defaultChecked?: boolean;
}

const RadioInput = React.forwardRef<HTMLInputElement, Props>(
  ({ name, value, defaultChecked = false, ...rest }, ref) => {
    return (
      <label>
        <input
          type="radio"
          name={name}
          ref={ref}
          value={value}
          className="d-none"
          defaultChecked={defaultChecked}
          {...rest}
        />
        <div className="radio-option">{value}</div>
      </label>
    );
  },
);

export default RadioInput;
