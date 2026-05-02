import "./BookCard.css";
import noImage from "../../assets/no-image.svg";
import Button from "../Button";
import FiveStars from "../FiveStars";
import Price from "../Price";
import { Book } from "../../queries/useTopRatedBooks";

interface Props {
  book?: Book;
}

function BookCard({ book }: Props) {
  return (
    <div className="book-card">
      <a href="#">
        <img className="book-card__img" src={noImage} alt="Image of the book" />
      </a>
      <div className="book-card__body">
        <h2 className="book-card__title">{book?.title || "Book title"}</h2>
        <h3 className="book-card__author">{book?.author || "Author McName"}</h3>
        <div className="book-card__badges">
          <FiveStars className="book-card__stars" rate={4} />
          <Price className="book-card__price">{book?.price || 11.11}</Price>
        </div>
        <Button className="btn-primary book-card__btn"> Add to Cart</Button>
      </div>
    </div>
  );
}

export default BookCard;
