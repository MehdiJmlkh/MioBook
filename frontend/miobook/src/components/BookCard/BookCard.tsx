import { useState } from "react";
import noImage from "../../assets/no-image.svg";
import { useNoScroll } from "../../hooks/useNoScroll";
import { Book } from "../../services/bookService";
import AddToCartModal from "../AddToCartModal";
import Backdrop from "../Backdrop";
import Button from "../Button";
import FiveStars from "../FiveStars";
import Price from "../Price";
import "./BookCard.css";

interface Props {
  book?: Book;
}

function BookCard({ book }: Props) {
  const [showCartModal, setShowCartModal] = useState(false);

  useNoScroll([showCartModal]);

  return (
    <div className="book-card">
      <Backdrop enabled={showCartModal} />
      <AddToCartModal
        className={showCartModal ? "show" : ""}
        onClose={() => setShowCartModal(false)}
        price={book?.price}
      />
      <a href="#">
        <img className="book-card__img" src={noImage} alt="Image of the book" />
      </a>
      <div className="book-card__body">
        <h2 className="book-card__title">{book?.title || "Book title"}</h2>
        <h3 className="book-card__author">{book?.author || "Author McName"}</h3>
        <div className="book-card__badges">
          <FiveStars
            className="book-card__stars"
            rate={book?.averageRating || 0}
          />
          <Price className="book-card__price">{book?.price || 11.11}</Price>
        </div>
        <Button className="btn-primary" onClick={() => setShowCartModal(true)}>
          Add to Cart
        </Button>
      </div>
    </div>
  );
}

export default BookCard;
