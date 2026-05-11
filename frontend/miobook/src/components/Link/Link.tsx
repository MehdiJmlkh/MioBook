import { ReactNode } from "react";
import { Link as RouterLink } from "react-router-dom";
import "./Link.css";

interface Props {
  children: ReactNode;
  to?: string;
}

const Link = ({ children, to = "" }: Props) => {
  return (
    <RouterLink to={to} className="link">
      {children}
    </RouterLink>
  );
};

export default Link;
