import "./Hero.css";
import bookshelf from "../../assets/bookshelf.svg";

const Hero = () => {
  return (
    <div className="hero">
      <img className="hero_image" src={bookshelf} alt="" />

      <div className="hero__content">
        <p>
          Welcome to MioBook – the online bookstore where you can buy or borrow
          books with ease.
        </p>
        <p>
          Whether you’re looking for the latest bestseller, a classic novel, or
          a niche title, MioBook has you covered.
        </p>
        <p>
          Here, you can quickly find books by title, author, and genre. And if
          you’re not sure to buy? Try borrowing instead! Rent a book for just a
          fraction of the price and enjoy full access for a set period.
        </p>
        <p>
          Your next great read is just a click away. Visit MioBook today and let
          the perfect book find you!
        </p>
      </div>
    </div>
  );
};

export default Hero;
