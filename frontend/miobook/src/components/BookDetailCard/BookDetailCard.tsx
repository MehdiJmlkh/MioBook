import { Book } from "../../services/bookService";
import Button from "../Button";
import FiveStars from "../FiveStars";
import ImageWithBadge from "../ImageWithBadge";
import Price from "../Price";
import "./BookDetailCard.css";

interface Props {
  book?: Book;
  onAddToCart: () => void;
}

const BookDetailCard = ({ onAddToCart, book }: Props) => {
  return (
    <div className="book">
      <ImageWithBadge className="book__img" />

      <div className="book__content">
        <div className="book__details">
          <h1 className="book__title">{book?.title}</h1>

          <div className="book__rating-block">
            <FiveStars
              className="book__stars"
              rate={book?.averageRating || 0}
            />
            <span className="book__rate">{book?.averageRating}</span>
          </div>

          <div className="book-detail">
            <h2 className="book-detail__title">Author</h2>
            <p className="book-detail__value">{book?.author}</p>
          </div>

          <div className="book-detail">
            <h2 className="book-detail__title">Publisher</h2>
            <p className="book-detail__value">{book?.publisher}</p>
          </div>

          <div className="book-detail">
            <h2 className="book-detail__title">Year</h2>
            <p className="book-detail__value">{book?.year}</p>
          </div>

          <div className="book-detail">
            <h2 className="book-detail__title">Genre</h2>
            <p className="book-detail__value">{book?.genres.join(", ")}</p>
          </div>
        </div>

        <div className="book__about">
          <h2 className="book-detail__title">About</h2>
          <p className="book-detail__value">
            {book?.synopsis} Lorem ipsum dolor sit amet consectetur adipisicing
            elit. Mollitia hic aut nobis sunt architecto quisquam qui, harum sit
            totam debitis repellendus ut molestiae autem itaque! Deleniti
            ratione repellendus ipsa maiores. Esse sequi dolore beatae minima
            enim, ducimus dignissimos placeat non omnis maiores repellendus,
            laborum inventore! Iusto ducimus beatae tempora voluptas.
          </p>
        </div>

        <Price className="book__price">{book?.price || 0}</Price>
        <Button className="btn btn-primary book__btn" onClick={onAddToCart}>
          Add to Cart
        </Button>
      </div>
    </div>
  );
};

export default BookDetailCard;
