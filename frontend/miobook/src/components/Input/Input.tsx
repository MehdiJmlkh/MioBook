import "./Input.css";
import React from "react";

interface Props extends React.InputHTMLAttributes<HTMLInputElement> {
  className?: string;
  error?: string;
}

const Input = React.forwardRef<HTMLInputElement, Props>(
  ({ className, error, ...rest }, ref) => {
    return (
      <div>
        <input className={`form-control ${className}`} {...rest} ref={ref} />
        {error && <p className="text-danger  error">{error}</p>}
      </div>
    );
  },
);

export default Input;
