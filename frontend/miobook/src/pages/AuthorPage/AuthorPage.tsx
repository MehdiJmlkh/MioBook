import Header from "../../components/Header";
import AuthorBackground from "../../assets/author-background.svg";
import AuthorImage from "../../assets/author-image.svg";
import "./AuthorPage.css";
import BookCard from "../../components/BookCard";
import Grid from "../../components/Grid";
import Footer from "../../components/Footer";

const AuthorPage = () => {
  return (
    <body className="author-page">
      <Header className="author-page__header" />
      <div className="author-page__heading">
        <img
          className="author-page__background"
          src={AuthorBackground}
          alt=""
        />
        <img className="author-page__author-image" src={AuthorImage} alt="" />
      </div>
      <main>
        <div className="author-page__author">
          <h1 className="author-page__name">Author Name</h1>
          <div className="author-details">
            <span className="author-detail">
              <h2 className="author-detail__title">Pen Name</h2>
              <p className="author-detail__value">Authorica</p>
            </span>

            <span className="author-detail">
              <h2 className="author-detail__title">Nationality</h2>
              <p className="author-detail__value">German</p>
            </span>

            <span className="author-detail">
              <h2 className="author-detail__title">Born</h2>
              <p className="author-detail__value">1998-02-01</p>
            </span>

            <span className="author-detail">
              <h2 className="author-detail__title">Died</h2>
              <p className="author-detail__value">2024-10-24</p>
            </span>

            <span className="author-detail">
              <h2 className="author-detail__title">Books</h2>
              <p className="author-detail__value">16</p>
            </span>
          </div>
        </div>
        <Grid>
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
        </Grid>
      </main>
      <Footer />
    </body>
  );
};

export default AuthorPage;
