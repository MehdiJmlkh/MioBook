import { useParams } from "react-router-dom";
import AuthorBackground from "../../assets/author-background.svg";
import AuthorImage from "../../assets/author-image.svg";
import BookCard from "../../components/BookCard";
import Grid from "../../components/Grid";
import { useAuthor } from "../../queries/useAuthor";
import "./AuthorPage.css";
import { useBooksByAuthor } from "../../queries/useBooksByAuthor";

const AuthorPage = () => {
  const { id } = useParams();
  const idNumber = parseInt(id || "");
  const { data: author } = useAuthor(idNumber);
  const { data: books } = useBooksByAuthor(idNumber);

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
              <p className="author-detail__value">{author?.died}</p>
            </span>

            <span className="author-detail">
              <h2 className="author-detail__title">Books</h2>
              <p className="author-detail__value">{books?.length}</p>
            </span>
          </div>
        </div>
        <Grid>
          {books?.map(book => <BookCard book={book}/>)}
        </Grid>
      </main>
    </body>
  );
};

export default AuthorPage;
