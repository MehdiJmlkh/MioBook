import Pagination from "../Pagination";
import ReviewCard from "../ReviewCard";
import "./ReviewsBlock.css";
import ReviewsIcon from "../../assets/reviews-icon.svg";
import Button from "../Button";
import { useState } from "react";
import { useReviews } from "../../queries/useReviews";

interface Props {
  onClickAddReview: () => void;
}

const ReviewsBlock = ({ onClickAddReview }: Props) => {
  const [page, setPage] = useState(1);
  const pageSize = 4;
  const { data: reviewList } = useReviews("Crimson Wake", { page, size: pageSize });

  return (
    <div className="block-review">
      <div className="block-review__heading">
        <div>
          <span className="block-review__heading__title"> Reviews </span>
          <span className="block-review__heading__counts">
            {reviewList?.totalReviews}
          </span>
        </div>
        <Button className="btn-secondary" onClick={onClickAddReview}>
          Add reviews
          <img className="review-icon" src={ReviewsIcon} alt="" />
        </Button>
      </div>
      {reviewList?.reviews.map((review) => (
        <ReviewCard review={review} />
      ))}
      <Pagination
        pageNumber={page}
        totalPages={Math.ceil((reviewList?.totalReviews || 1) / pageSize)}
        onClick={(page) => {
          console.log(page);
          setPage(page);
        }}
      />
    </div>
  );
};

export default ReviewsBlock;
