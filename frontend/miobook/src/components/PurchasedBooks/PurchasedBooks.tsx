import Table from "../Table";
import "./PurchasedBooks.css";
import BookImage from "../../assets/book.svg";
import BookIcon from "../../assets/book-icon.svg";
import Button from "../Button";
import Card from "../Card";
import { usePurchasedBooks } from "../../queries/purchases/usePurchasedBooks";
import BorrowedBadge from "../BorrowedBadge";
import { useNavigate, useParams } from "react-router-dom";
import Link from "../Link";
import NoResult from "../../assets/no-result.svg";
import { useAuth } from "../../queries/auth/useAuth";

interface Props {
  className?: string;
}

const PurchasedBooks = ({ className }: Props) => {
  const {data: user} = useAuth()
  const { data: purchasedBooksHistory } = usePurchasedBooks(user?.username);

  const navigate = useNavigate();

  return (
    <Card
      className={className}
      title="My Books"
      icon={<img src={BookIcon} alt="" />}
    >
      {purchasedBooksHistory?.books.length === 0 ? (
        <div className="empty-list__content">
          <img className="my-book__no-result" src={NoResult} alt="" />
          <p>No Book</p>
        </div>
      ) : (
        <Table>
          <thead>
            <tr>
              <th>Image</th>
              <th>Name</th>
              <th>Author</th>
              <th>Genre</th>
              <th>Publisher</th>
              <th>Published Year</th>
              <th>Status</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {purchasedBooksHistory?.books.map((book) => (
              <tr>
                <td className="table-image-row">
                  <Link to={`/books/${book.id}`}>
                    <img className="table-image" src={BookImage} alt="" />
                  </Link>
                </td>
                <td data-label="Name">
                  <Link
                    to={`/books/${book.id}`}
                    className="purchased-book__title"
                  >
                    {book.title}
                  </Link>
                </td>
                <td data-label="Author">
                  <Link
                    to={`/authors/${book.authorId}`}
                    className="purchased-book__author"
                  >
                    {book.author}
                  </Link>
                </td>
                <td data-label="Genre">{book.genres.join(", ")}</td>
                <td data-label="Publisher">{book.publisher}</td>
                <td data-label="Published Year">{book.year}</td>
                <td data-label="Status">
                  {book.isBorrowed ? (
                    <BorrowedBadge expiredDate={book.expiredDate} />
                  ) : (
                    "Owned"
                  )}
                </td>
                <td className="table-btn-row">
                  <Button
                    className="btn-secondary"
                    onClick={() => navigate(`/books/${book?.id}/content`)}
                  >
                    Read
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </Card>
  );
};

export default PurchasedBooks;
