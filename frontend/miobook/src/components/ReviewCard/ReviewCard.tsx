import { Review } from "../../services/reviewService";
import FiveStars from "../FiveStars";
import "./ReviewCard.css";

interface Props {
  review: Review;
  className?: string;
}

const ReviewsCard = ({ review, className }: Props) => {
  return (
    <div className={`review ${className}`}>
      <div className="review__avatar"> TW </div>
      <h3 className="review__heading">{review.username}</h3>
      <p className="review__content">{review.comment}</p>
      <FiveStars className="review__rating" rate={review.rate} />
      <span className="review__date">{review.date}</span>
    </div>
  );
};

export default ReviewsCard;
