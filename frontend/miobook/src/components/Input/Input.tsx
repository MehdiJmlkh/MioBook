import "./Input.css";
import React from "react";

interface Props extends React.InputHTMLAttributes<HTMLInputElement> {
  className?: string;
}

const Input = React.forwardRef<HTMLInputElement, Props>(
  ({ className, ...rest }, ref) => {
    return (
      <input className={`form-control ${className}`} {...rest} ref={ref} />
    );
  },
);

export default Input;
