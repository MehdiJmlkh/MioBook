import { FaStar, FaRegStar, FaStarHalfAlt } from "react-icons/fa";
import "./FiveStars.css";

interface Props {
  rate: number;
  className?: string;
}

const FiveStars = ({ rate, className }: Props) => {
  return (
    <span className="five-stars">
      {[1, 2, 3, 4, 5].map((n) =>
        rate >= n ? (
          <FaStar key={n} className={`star star--filled ${className}`} />
        ) : rate >= n - 0.5 ? (
          <FaStarHalfAlt key={n} className={`star star--filled ${className}`} />
        ) : (
          <FaRegStar key={n} className={`star ${className}`} />
        ),
      )}
    </span>
  );
};

export default FiveStars;
