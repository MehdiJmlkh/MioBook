import { useState } from "react";
import AddReviewModal from "../../components/AddReviewModal";
import AddToCartModal from "../../components/AddToCartModal";
import Backdrop from "../../components/Backdrop";
import BookDetailCard from "../../components/BookDetailCard";
import ReviewsBlock from "../../components/ReviewsBlock";
import { useNoScroll } from "../../hooks/useNoScroll";

import "./BookPage.css";

const BookPage = () => {
  const [showReviewModal, setShowReviewModal] = useState(false);
  const [showCartModal, setShowCartModal] = useState(false);

  useNoScroll([showReviewModal, showCartModal]);

  return (
    <div className="page-container">
      <Backdrop enabled={showReviewModal || showCartModal} />
      <AddToCartModal
        className={showCartModal ? "show" : ""}
        onClose={() => setShowCartModal(false)}
      />
      <AddReviewModal
        className={showReviewModal ? "show" : ""}
        onClose={() => setShowReviewModal(false)}
      />
      <BookDetailCard onAddToCart={() => setShowCartModal(true)} />
      <ReviewsBlock onClickAddReview={() => setShowReviewModal(true)} />
    </div>
  );
};

export default BookPage;
