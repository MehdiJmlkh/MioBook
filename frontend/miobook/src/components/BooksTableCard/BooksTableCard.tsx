import BookIcon from "../../assets/book-icon.svg";
import BookImage from "../../assets/book.svg";
import { useBooks } from "../../queries/books/useBooks";
import Card from "../Card";
import Price from "../Price";
import Table from "../Table";
import "./BooksTableCard.css";
import NoResult from "../../assets/no-result.svg";
import EmptyIcon from "../EmptyIcon";

interface Props {
  className?: string;
}

const BooksTableCard = ({ className }: Props) => {
  const { data: books } = useBooks();

  return (
    <Card
      className={className}
      title="Books"
      icon={<img src={BookIcon} alt="" />}
    >
      {books?.length === 0 ? (
        <EmptyIcon src={NoResult} description="No Book" />
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
              <th>Price</th>
              <th>Total Buys</th>
            </tr>
          </thead>
          <tbody>
            {books?.map((book) => (
              <tr>
                <td className="table-image-row">
                  <img className="table-image" src={BookImage} alt="" />
                </td>
                <td data-label="Name">{book.title}</td>
                <td data-label="Author">{book.author}</td>
                <td data-label="Genre">{book.genres.join(", ")}</td>
                <td data-label="Publisher">{book.publisher}</td>
                <td data-label="Published Year">{book.year}</td>
                <td data-label="Price">
                  <Price>{book.price}</Price>
                </td>
                <td data-label="Total Buys">10</td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </Card>
  );
};

export default BooksTableCard;
