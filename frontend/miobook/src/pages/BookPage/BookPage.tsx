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
  const [openAddReview, setOpenAddReview] = useState(false);
  const [openAddToCart, setOpenAddToCart] = useState(false);

  useNoScroll([openAddReview, openAddToCart]);

  return (
    <body>
      <Header className="header" />
      <main>
        <Backdrop enabled={openAddReview || openAddToCart} />
        <AddToCartModal
          className={openAddToCart ? "open" : ""}
          onClose={() => setOpenAddToCart(false)}
        />
        <AddReviewModal
          className={openAddReview ? "open" : ""}
          onClose={() => setOpenAddReview(false)}
        />
        <BookDetailCard onAddToCart={() => setOpenAddToCart(true)} />
        <ReviewsBlock onClickAddReview={() => setOpenAddReview(true)} />
      </main>
      <Footer />
    </body>
  );
};

export default BookPage;
