import { ReactNode } from "react";
import "./Link.css";

interface Props {
  children: ReactNode;
}

const Link = ({ children }: Props) => {
  return (
    <a href="#" className="link">
      {children}
    </a>
  );
};

export default Link;
