import "./Input.css";
import React from "react";

interface Props extends React.InputHTMLAttributes<HTMLInputElement> {}

const Input = React.forwardRef<HTMLInputElement, Props>(({ ...rest }, ref) => {
  return <input className="form-control" {...rest} ref={ref} />;
});

export default Input;
