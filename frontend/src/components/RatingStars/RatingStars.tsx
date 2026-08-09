import { useState } from "react";
import Star from "../Star";
import "./RatingStars.css";

interface Props {
  onChange: (rate: number) => void;
  value: number;
}

const RatingStars = ({ onChange, value }: Props) => {
  return (
    <div className="rating-stars">
      {Array.from({ length: 5 }).map((_, i) => (
        <Star
          key={i}
          isFilled={i + 1 <= value}
          onClick={() => onChange(i + 1)}
        />
      ))}
    </div>
  );
};

export default RatingStars;
