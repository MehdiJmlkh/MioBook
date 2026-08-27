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
import { useNavigate } from "react-router-dom";
import {
  BookStatus,
  useBookStatus,
} from "../../queries/purchases/useBookStatus";
import { useAuth } from "../../queries/auth/useAuth";
import Link from "../Link";

interface Props {
  book?: Book;
}

function BookCard({ book }: Props) {
  const [showCartModal, setShowCartModal] = useState(false);
  const { data: user } = useAuth();
  const { data: status } = useBookStatus(user?.username, book?.id);
  const purchased =
    status === BookStatus.Owned || status === BookStatus.Borrowed;
  const navigate = useNavigate();

  useNoScroll([showCartModal]);

  return (
    <div className="book-card">
      <Backdrop enabled={showCartModal} />
      <AddToCartModal
        className={showCartModal ? "show" : ""}
        onClose={() => setShowCartModal(false)}
        price={book?.price}
        bookTitle={book?.title}
      />
      <Link to={`/books/${book?.id}`}>
        <img className="book-card__img" src={noImage} alt="Image of the book" />
      </Link>
      <div className="book-card__body">
        <h2 className="book-card__title">{book?.title || "Book title"}</h2>
        <h3 className="book-card__author">
          <Link to={`/authors/${book?.authorId}`}>
            {book?.author || "Author McName"}
          </Link>
        </h3>

        <div className="book-card__badges">
          <FiveStars
            className="book-card__stars"
            rate={book?.averageRating || 0}
          />
          <Price className="book-card__price">{book?.price || 11.11}</Price>
        </div>
        <Button
          className="btn-primary"
          onClick={() =>
            purchased
              ? navigate(`/books/${book?.id}/content`)
              : setShowCartModal(true)
          }
        >
          {purchased ? "Read" : "Add to Cart"}
        </Button>
      </div>
    </div>
  );
}

export default BookCard;
