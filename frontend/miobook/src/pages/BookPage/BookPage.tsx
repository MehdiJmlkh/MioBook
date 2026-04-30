import { useState } from "react";
import AddReviewModal from "../../components/AddReviewModal";
import Backdrop from "../../components/Backdrop";
import BookDetailCard from "../../components/BookDetailCard";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import ReviewsBlock from "../../components/ReviewsBlock";
import "./BookPage.css";
import { useNoScroll } from "../../hooks/useNoScroll";
import AddToCartModal from "../../components/AddToCartModal";

const BookPage = () => {
  const [showReviewModal, setShowReviewModal] = useState(false);
  const [openAddToCart, setOpenAddToCart] = useState(false);

  useNoScroll([showReviewModal, openAddToCart]);

  return (
    <body>
      <Header className="header" />
      <main>
        <Backdrop enabled={showReviewModal || openAddToCart} />
        <AddToCartModal
          className={openAddToCart ? "open" : ""}
          onClose={() => setOpenAddToCart(false)}
        />
        <AddReviewModal
          className={showReviewModal ? "open" : ""}
          onClose={() => setShowReviewModal(false)}
        />
        <BookDetailCard onAddToCart={() => setOpenAddToCart(true)} />
        <ReviewsBlock onClickAddReview={() => setShowReviewModal(true)} />
      </main>
      <Footer />
    </body>
  );
};

export default BookPage;
