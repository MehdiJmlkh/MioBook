import "./BookCard.css";
import noImage from "../../assets/no-image.svg";
import Button from "../Button";
import FiveStars from "../FiveStars";
import Price from "../Price";

function BookCard() {
  return (
    <div className="book-card">
      <a href="#">
        <img
          className="book-card__img"
          src={noImage}
          alt="Image of the book"
        />
      </a>
      <div className="book-card__body">
        <h2 className="book-card__title">Book Title</h2>
        <h3 className="book-card__author">Author McName</h3>
        <div className="book-card__badges">
          <FiveStars className="book-card__stars" rate={4} />
          <Price className="book-card__price">10.25</Price>
        </div>
        <Button className="btn-primary book-card__btn"> Add to Cart</Button>
      </div>
    </div>
  );
}

export default BookCard;
