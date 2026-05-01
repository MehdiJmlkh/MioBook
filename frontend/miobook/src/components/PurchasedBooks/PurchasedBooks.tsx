import Table from "../Table";
import "./PurchasedBooks.css";
import BookImage from "../../assets/book.svg";
import BookIcon from "../../assets/book-icon.svg";
import Button from "../Button";
import Card from "../Card";

interface Props {
  className?: string;
}

const PurchasedBooks = ({ className }: Props) => {
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
          <tr>
            <td className="table-image-row">
              <img className="table-image" src={BookImage} alt="" />
            </td>
            <td data-label="Name">The Design of Books</td>
            <td data-label="Author">Debbie Berne</td>
            <td data-label="Genre">Guide</td>
            <td data-label="Publisher">The Publishers</td>
            <td data-label="Published Year">2024</td>
            <td data-label="Status">Owned</td>
            <td className="table-btn-row">
              <Button className="btn-secondary">Read</Button>
            </td>
          </tr>
          <tr>
            <td>
              <img className="table-image" src={BookImage} alt="" />
            </td>
            <td data-label="Name">The Design of Books</td>
            <td data-label="Author">Debbie Berne</td>
            <td data-label="Genre">Guide</td>
            <td data-label="Publisher">The Publishers</td>
            <td data-label="Published Year">2024</td>
            <td data-label="Status">Owned</td>
            <td className="table-btn-row">
              <Button className="btn-secondary">Read</Button>
            </td>
          </tr>
        </tbody>
      </Table>
    </Card>
  );
};

export default PurchasedBooks;
