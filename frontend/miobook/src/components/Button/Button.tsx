import { ReactNode } from "react";
import "./Button.css";

interface Props {
  className?: string;
  children: ReactNode;
  disabled?: boolean;
  onClick?: () => void;
}

const Button = ({ className, children, disabled, onClick }: Props) => {
  return (
    <button
      className={`btn ${className}`}
      disabled={disabled}
      onClick={onClick}
      type="submit"
    >
      {children}
    </button>
  );
};

export default Button;
