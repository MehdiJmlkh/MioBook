import Table from "../Table";
import "./PurchasedBooks.css";
import BookImage from "../../assets/book.svg";
import BookIcon from "../../assets/book-icon.svg";
import Button from "../Button";
import Card from "../Card";
import { usePurchasedBooks } from "../../queries/usePurchasedBooks";

interface Props {
  className?: string;
}

const PurchasedBooks = ({ className }: Props) => {
  const { data: purchasedBooksHistory } = usePurchasedBooks("li_wei");

  return (
    <Card
      className={className}
      title="My Books"
      icon={<img src={BookIcon} alt="" />}
    >
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
                <img className="table-image" src={BookImage} alt="" />
              </td>
              <td data-label="Name">{book.title}</td>
              <td data-label="Author">{book.author}</td>
              <td data-label="Genre">{book.genres.join(", ")}</td>
              <td data-label="Publisher">{book.publisher}</td>
              <td data-label="Published Year">{book.year}</td>
              <td data-label="Status">{book.isBorrowed ? "Borrowed" : "Owned"}</td>
              <td className="table-btn-row">
                <Button className="btn-secondary">Read</Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Card>
  );
};

export default PurchasedBooks;
