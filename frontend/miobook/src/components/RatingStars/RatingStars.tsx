import { useState } from "react";
import Star from "../Star";
import "./RatingStars.css";

const RatingStars = () => {
  const [rating, setRating] = useState(0);

  return (
    <div className="rating-stars">
      {Array.from({ length: 5 }).map((_, i) => (
        <Star
          key={i}
          isFilled={i + 1 <= rating}
          onClick={() => setRating(i + 1)}
        />
      ))}
    </div>
  );
};

export default RatingStars;
