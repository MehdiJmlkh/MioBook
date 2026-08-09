import { ReactNode } from "react";
import "./RadioOptions.css";

interface Props {
  children: ReactNode;
}

const RadioOptions = ({ children }: Props) => {
  return <div className="radio-options">{children}</div>;
};

export default RadioOptions;
