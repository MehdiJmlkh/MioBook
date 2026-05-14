import { useParams } from "react-router-dom";
import AuthorBackground from "../../assets/author-background.svg";
import AuthorImage from "../../assets/author-image.svg";
import BookCard from "../../components/BookCard";
import Grid from "../../components/Grid";
import { useAuthor } from "../../queries/useAuthor";
import "./AuthorPage.css";
import { useBooksByAuthor } from "../../queries/useBooksByAuthor";
import Pagination from "../../components/Pagination";
import { useState } from "react";

const AuthorPage = () => {
  const { id } = useParams();
  const idNumber = parseInt(id || "");
  const { data: author } = useAuthor(idNumber);

  const [page, setPage] = useState(1);
  const pageSize = 10;

  const { data: bookPage } = useBooksByAuthor(idNumber, {
    page: page,
    size: pageSize,
  });

  return (
    <body className="author-page">
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
          <h1 className="author-page__name">{author?.name}</h1>
          <div className="author-details">
            <span className="author-detail">
              <h2 className="author-detail__title">Pen Name</h2>
              <p className="author-detail__value">{author?.penName}</p>
            </span>

            <span className="author-detail">
              <h2 className="author-detail__title">Nationality</h2>
              <p className="author-detail__value">{author?.nationality}</p>
            </span>

            <span className="author-detail">
              <h2 className="author-detail__title">Born</h2>
              <p className="author-detail__value">{author?.born}</p>
            </span>

            <span className="author-detail">
              <h2 className="author-detail__title">Died</h2>
              <p className="author-detail__value">{author?.died || <>&mdash;</>}</p>
            </span>

            <span className="author-detail">
              <h2 className="author-detail__title">Books</h2>
              <p className="author-detail__value">{bookPage?.totalBooks}</p>
            </span>
          </div>
        </div>
        <Grid>
          {bookPage?.books.map((book) => (
            <BookCard book={book} />
          ))}
        </Grid>
        <Pagination
          totalPages={Math.ceil((bookPage?.totalBooks || 1) / pageSize)}
          pageNumber={page}
          onClick={(page) => setPage(page)}
        />
      </main>
    </body>
  );
};

export default AuthorPage;
