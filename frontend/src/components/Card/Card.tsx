import { ReactNode } from "react";
import "./Card.css";

interface Props {
  className?: string;
  children: ReactNode;
  title: string;
  icon: ReactNode;
}

const Card = ({ className, children, title, icon }: Props) => {
  return (
    <div className={`simple-card ${className}`}>
      <div className="card__heading">
        <span className="card__icon">{icon}</span>
        <h2 className="card__title">{title}</h2>
      </div>
      {children}
    </div>
  );
};

export default Card;
