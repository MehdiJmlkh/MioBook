import { ReactNode } from "react";
import "./Button.css";

interface Props {
  className?: string;
  children: ReactNode;
  disabled?: boolean;
  onClick?: () => void;
  type?: "submit" | "reset" | "button" | undefined;
}

const Button = ({ className, children, disabled, onClick, type="submit" }: Props) => {
  return (
    <button
      className={`btn ${className}`}
      disabled={disabled}
      onClick={onClick}
      type={type}
    >
      {children}
    </button>
  );
};

export default Button;
