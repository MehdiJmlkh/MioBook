import "./BookDetailCard.css";
import ImageWithBadge from "../ImageWithBadge";
import FiveStars from "../FiveStars";
import Price from "../Price";
import Button from "../Button";

interface Props {
  onAddToCart: () => void;
}

const BookDetailCard = ({ onAddToCart }: Props) => {
  return (
    <div className="book">
      <ImageWithBadge className="book__img" />

      <div className="book__content">
        <div className="book__details">
          <h1 className="book__title">Book Title</h1>

          <div className="book__rating-block">
            <FiveStars className="book__stars" rate={4} />
            <span className="book__rate">5.0</span>
          </div>

          <div className="book-detail">
            <h2 className="book-detail__title">Author</h2>
            <p className="book-detail__value">Author McName</p>
          </div>

          <div className="book-detail">
            <h2 className="book-detail__title">Publisher</h2>
            <p className="book-detail__value">The Publishers</p>
          </div>

          <div className="book-detail">
            <h2 className="book-detail__title">Year</h2>
            <p className="book-detail__value">2025</p>
          </div>

          <div className="book-detail">
            <h2 className="book-detail__title">Genre</h2>
            <p className="book-detail__value">Comedy, Romance</p>
          </div>
        </div>

        <div className="book__about">
          <h2 className="book-detail__title">About</h2>
          <p className="book-detail__value">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Provident
            nemo inventore nam laboriosam dicta voluptatum suscipit quisquam
            ullam veritatis nesciunt cum officiis expedita, earum molestias
            sapiente, reiciendis minus blanditiis, nisi eveniet porro totam.
            Temporibus, sequi? Provident quidem tenetur fuga beatae,
          </p>
        </div>

        <Price className="book__price">18.99</Price>
        <Button className="btn btn-primary book__btn" onClick={onAddToCart}>
          Add to Cart
        </Button>
      </div>
    </div>
  );
};

export default BookDetailCard;
