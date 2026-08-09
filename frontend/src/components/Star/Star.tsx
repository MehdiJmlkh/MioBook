import { useState } from "react";
import "./Star.css";
import { FaStar, FaRegStar } from "react-icons/fa";

interface Props {
  isFilled: boolean;
  onClick: () => void;
}

const Star = ({ isFilled, onClick }: Props) => {
  return (
    <span className="star-container" onClick={onClick}>
      {isFilled ? (
        <FaStar className="single-star star--filled" />
      ) : (
        <FaRegStar className="single-star" />
      )}
    </span>
  );
};

export default Star;
