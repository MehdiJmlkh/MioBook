import { useState } from "react";
import AddReviewModal from "../../components/AddReviewModal";
import Backdrop from "../../components/Backdrop";
import BookDetailCard from "../../components/BookDetailCard";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import ReviewsBlock from "../../components/ReviewsBlock";
import "./BookPage.css";
import { useNoScroll } from "../../hooks/useNoScroll";

const BookPage = () => {
  const [openAddReview, setOpenAddReview] = useState(true);

  useNoScroll(openAddReview);

  return (
    <body>
      <Header className="header" />
      <main>
        <Backdrop enabled={openAddReview} />
        <AddReviewModal
          className={openAddReview ? "open" : ""}
          onClose={() => setOpenAddReview(false)}
        />
        <BookDetailCard />
        <ReviewsBlock onClickAddReview={() => setOpenAddReview(true)} />
      </main>
      <Footer />
    </body>
  );
};

export default BookPage;
