import Pagination from "../Pagination";
import ReviewCard from "../ReviewCard";
import "./ReviewsBlock.css";
import ReviewsIcon from "../../assets/reviews-icon.svg";
import Button from "../Button";
import { useState } from "react";

const ReviewsBlock = () => {
  const [pageNumber, setPageNumber] = useState(1);

  return (
    <div className="block-review">
      <div className="block-review__heading">
        <div>
          <span className="block-review__heading__title"> Reviews </span>
          <span className="block-review__heading__counts">130</span>
        </div>
        <Button className="btn-secondary">
          Add reviews
          <img className="review-icon" src={ReviewsIcon} alt="" />
        </Button>
      </div>
      <ReviewCard />
      <ReviewCard />
      <ReviewCard />
      <ReviewCard />
      <Pagination
        pageNumber={pageNumber}
        totalPages={5}
        onClick={(page) => setPageNumber(page)}
      />
    </div>
  );
};

export default ReviewsBlock;
