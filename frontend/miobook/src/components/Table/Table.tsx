import { ReactNode } from "react";
import "./Table.css";

interface Props {
  children: ReactNode;
}

const Table = ({ children }: Props) => {
  return <table className="table">{children}</table>;
};

export default Table;
