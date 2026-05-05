import { Review } from "../../queries/useReviews";
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
      <p className="review__content">
        {review.comment}
      </p>
      <FiveStars className="review__rating" rate={review.rate} />
      <span className="review__date"> February 20, 2025 </span>
    </div>
  );
};

export default ReviewsCard;
