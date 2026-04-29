import BookDetailCard from "../../components/BookDetailCard";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import ReviewsBlock from "../../components/ReviewsBlock";
import "./BookPage.css";

const BookPage = () => {
  return <body>
    <Header className="header" />
    <main>
        <BookDetailCard />
        <ReviewsBlock />
    </main>
    <Footer />
  </body>;
};

export default BookPage;
