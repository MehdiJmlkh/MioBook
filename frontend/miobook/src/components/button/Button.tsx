import { ReactNode } from "react";
import "./Button.css";

interface Props {
  className?: string;
  children: ReactNode;
  disabled?: boolean;
}

const Button = ({ className, children, disabled }: Props) => {
  return (
    <button className={`btn ${className}`} disabled={disabled}>
      {children}
    </button>
  );
};

export default Button;
