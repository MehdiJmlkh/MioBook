import noImage from "../../assets/book.svg";
import BookImage from "../BookImage";
import Button from "../Button";
import Link from "../Link";
import Price from "../Price";
import "./BookItemRow.css";

interface Item {
  bookId: number;
  authorId: number;
  title: string;
  author: string;
  isBorrowed: boolean;
  borrowDays?: number;
  price: number;
  imageLink: string;
  finalPrice: number;
}

interface Props<T> {
  item: T;
  addBtn?: boolean;
  onClick?: () => void;
}

const BookItemRow = <T extends Item>({ item, addBtn, onClick }: Props<T>) => {
  return (
    <tr>
      <td className="table-image-row">
        <Link to={`/books/${item.bookId}`}>
          <BookImage
            className="table-image"
            imageLink={item.imageLink}
            noImage={noImage}
          />
        </Link>
      </td>
      <td data-label="Name">
        <Link className="link--padded" to={`/books/${item.bookId}`}>
          {item.title}
        </Link>
      </td>
      <td data-label="Author">
        <Link className="link--padded" to={`/authors/${item.authorId}`}>
          {item.author}
        </Link>
      </td>
      <td data-label="Price">
        <span>
          {item.isBorrowed && (
            <>
              <Price className="line-through">{item.price}</Price>{" "}
            </>
          )}
          <Price>{item.finalPrice}</Price>
        </span>
      </td>
      <td data-label="Borrow Days">
        {item.isBorrowed ? item.borrowDays : "Not Borrowed"}
      </td>
      {addBtn && (
        <td className="table-btn-row">
          <Button className="btn-secondary" onClick={onClick}>
            Remove
          </Button>
        </td>
      )}
    </tr>
  );
};

export default BookItemRow;
