import { HiChevronDown } from "react-icons/hi";
import "./ExpandableRow.css";
import { ReactNode, useState } from "react";

interface Props {
  title: ReactNode;
  children: ReactNode;
}

const ExpandableRow = ({ title, children }: Props) => {
  const [show, setShow] = useState(false);

  return (
    <div className={`expandable-row ${show ? "expandable-row--open" : ""}`}>
      <div className={`expandable-row__heading`} onClick={() => setShow(!show)}>
        <span>{title}</span>
        <HiChevronDown className="expandable-row__chevron" />
      </div>
      <div className={`expandable-row__content`}>{children}</div>
    </div>
  );
};

export default ExpandableRow;
