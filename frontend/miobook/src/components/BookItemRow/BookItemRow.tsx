import BookImage from "../../assets/book.svg";
import Button from "../Button";
import Price from "../Price";
import "./BookItemRow.css";

interface Item {
  title: string;
  author: string;
  isBorrowed: boolean;
  borrowDays?: number;
  price: number;
  finalPrice: number;
}

interface Props<T> {
  item: T;
  addBtn?: boolean;
}

const BookItemRow = <T extends Item>({ item, addBtn }: Props<T>) => {
  return (
    <tr>
      <td className="table-image-row">
        <img className="table-image" src={BookImage} alt="" />
      </td>
      <td data-label="Name">{item.title}</td>
      <td data-label="Author">{item.author}</td>
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
          <Button className="btn-secondary">Remove</Button>
        </td>
      )}
    </tr>
  );
};

export default BookItemRow;
