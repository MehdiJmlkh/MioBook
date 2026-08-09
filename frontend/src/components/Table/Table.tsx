import { ReactNode } from "react";
import "./Table.css";

interface Props {
  className?: string;
  children: ReactNode;
}

const Table = ({ className, children }: Props) => {
  return <table className={`table ${className}`}>{children}</table>;
};

export default Table;
