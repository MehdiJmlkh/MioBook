import { useState } from "react";
import AddReviewModal from "../../components/AddReviewModal";
import Backdrop from "../../components/Backdrop";
import BookDetailCard from "../../components/BookDetailCard";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import ReviewsBlock from "../../components/ReviewsBlock";
import { useNoScroll } from "../../hooks/useNoScroll";
import AddToCartModal from "../../components/AddToCartModal";

import "./BookPage.css";

const BookPage = () => {
  const [showReviewModal, setShowReviewModal] = useState(false);
  const [showCartModal, setShowCartModal] = useState(false);

  useNoScroll([showReviewModal, showCartModal]);

  return (
    <body>
      <Header className="header" />
      <main>
        <Backdrop enabled={showReviewModal || showCartModal} />
        <AddToCartModal
          className={showCartModal ? "open" : ""}
          onClose={() => setShowCartModal(false)}
        />
        <AddReviewModal
          className={showReviewModal ? "open" : ""}
          onClose={() => setShowReviewModal(false)}
        />
        <BookDetailCard onAddToCart={() => setShowCartModal(true)} />
        <ReviewsBlock onClickAddReview={() => setShowReviewModal(true)} />
      </main>
      <Footer />
    </body>
  );
};

export default BookPage;
