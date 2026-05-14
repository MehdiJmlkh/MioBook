import { useState } from "react";
import { useParams } from "react-router-dom";
import AddReviewModal from "../../components/AddReviewModal";
import AddToCartModal from "../../components/AddToCartModal";
import Backdrop from "../../components/Backdrop";
import BookDetailCard from "../../components/BookDetailCard";
import ReviewsBlock from "../../components/ReviewsBlock";
import { useNoScroll } from "../../hooks/useNoScroll";
import { useBook } from "../../queries/books/useBook";
import "./BookPage.css";
import { useBookStatus } from "../../queries/useBookStatus";

const BookPage = () => {
  const { id } = useParams();
  const { data: book, isLoading } = useBook(parseInt(id || ""));

  const [showReviewModal, setShowReviewModal] = useState(false);
  const [showCartModal, setShowCartModal] = useState(false);

  useNoScroll([showReviewModal, showCartModal]);

  if (isLoading) {
    return <p>Loading...</p>;
  }

  return (
    <div className="page-container">
      <Backdrop enabled={showReviewModal || showCartModal} />
      <AddToCartModal
        className={showCartModal ? "show" : ""}
        onClose={() => setShowCartModal(false)}
        price={book?.price}
        bookTitle={book?.title}
      />
      <AddReviewModal
        className={showReviewModal ? "show" : ""}
        onClose={() => setShowReviewModal(false)}
        bookTitle={book?.title}
        bookId={book?.id}
      />
      <BookDetailCard book={book} onAddToCart={() => setShowCartModal(true)} />
      <ReviewsBlock
        bookId={book?.id || 0}
        onClickAddReview={() => setShowReviewModal(true)}
      />
    </div>
  );
};

export default BookPage;
