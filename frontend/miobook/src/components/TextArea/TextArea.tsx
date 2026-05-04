import React from "react";
import "../Input/Input.css";
import "./TextArea.css";

interface Props {
  className?: string;
  rows: number;
  placeholder: string;
}

const TextArea = React.forwardRef<HTMLTextAreaElement, Props>(
  ({ className, rows, placeholder, ...rest }, ref) => {
    return (
      <textarea
        {...rest}
        ref={ref}
        className={`form-control form-control--dark-background ${className}`}
        id="description"
        rows={rows}
        placeholder={placeholder}
      ></textarea>
    );
  },
);

export default TextArea;
