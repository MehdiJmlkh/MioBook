import { ReactNode } from "react";
import "./Grid.css";

interface Props {
  children: ReactNode;
}

const Grid = ({ children }: Props) => {
  return <div className="grid">{children}</div>;
};

export default Grid;
