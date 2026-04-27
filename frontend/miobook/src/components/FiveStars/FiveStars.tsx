import { FaStar, FaRegStar } from "react-icons/fa";


import "./FiveStars.css";

interface Props {
  rate: number;
}

const FiveStars = ({ rate }: Props) => {
  return (
    <span className="five-stars">
      {[1, 2, 3, 4, 5].map((n) =>
        n <= rate ? (
          <FaStar key={n} className="star star--filled" />
        ) : (
          <FaRegStar key={n} className="star" />
        ),
      )}
    </span>
  );
};

export default FiveStars;
