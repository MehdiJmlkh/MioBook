import { ReactNode } from "react";
import { Link as RouterLink } from "react-router-dom";
import "./Link.css";

interface Props {
  children: ReactNode;
  to?: string;
  className?: string;
}

const Link = ({ children, to = "", className }: Props) => {
  return (
    <RouterLink to={to} className={`link ${className}`}>
      {children}
    </RouterLink>
  );
};

export default Link;
