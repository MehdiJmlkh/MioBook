import Card from "../Card";
import Table from "../Table";
import BookImage from "../../assets/book.svg";
import BookIcon from "../../assets/book-icon.svg";

import "./BooksTableCard.css";
import Button from "../Button";
import Price from "../Price";

interface Props {
  className?: string;
}
const BooksTableCard = ({ className }: Props) => {
  return (
    <Card
      className={className}
      title="Books"
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
            <th>Price</th>
            <th>Total Buys</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className="table-image-row">
              <img className="table-image" src={BookImage} alt="" />
            </td>
            <td data-label="Name">The Design of Books</td>
            <td data-label="Author">Debbie Berne</td>
            <td data-label="Genre">Guide</td>
            <td data-label="Publisher">The Publishers</td>
            <td data-label="Published Year">2024</td>
            <td data-label="Price">
              <Price>18.00</Price>
            </td>
            <td data-label="Total Buys">10</td>
          </tr>
          <tr>
            <td className="table-image-row">
              <img className="table-image" src={BookImage} alt="" />
            </td>
            <td data-label="Name">The Design of Books</td>
            <td data-label="Author">Debbie Berne</td>
            <td data-label="Genre">Guide</td>
            <td data-label="Publisher">The Publishers</td>
            <td data-label="Published Year">2024</td>
            <td data-label="Price">
              <Price>18.00</Price>
            </td>
            <td data-label="Total Buys">10</td>
          </tr>
        </tbody>
      </Table>
    </Card>
  );
};

export default BooksTableCard;
