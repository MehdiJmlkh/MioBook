import FiveStars from "../FiveStars";
import "./ReviewCard.css";

interface Props {
  className?: string;
}

const ReviewsCard = ({ className }: Props) => {
  return (
    <div className={`review ${className}`}>
      <div className="review__avatar"> TW </div>
      <h3 className="review__heading">The Person</h3>
      <p className="review__content">
        I bought it 3 weeks ago and now come back just to say "Awesome". I realy
        enjoy it.
      </p>
      <FiveStars className="review__rating" rate={4} />
      <span className="review__date"> February 20, 2025 </span>
    </div>
  );
};

export default ReviewsCard;
