import Card from "../Card";
import { RiHistoryLine } from "react-icons/ri";
import BookImage from "../../assets/book.svg";
import Price from "../Price";
import Button from "../Button";
import Table from "../Table";
import ExpandableRow from "../ExpandableRow";
import "./HistoryCard.css";

const HistoryCard = () => {
  return (
    <Card title="History" icon={<RiHistoryLine />}>
      <div className="history-table">
        {Array.from({ length: 3 }).map(() => {
          return (
            <ExpandableRow
              title={
                <span>
                  2025-03-24 16:04 | <Price>21.6</Price>
                </span>
              }
            >
              <Table className="history-table__contnent">
                <thead>
                  <tr>
                    <th>Image</th>
                    <th>Name</th>
                    <th>Author</th>
                    <th>Price</th>
                    <th>Borrow Days</th>
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
                    <td data-label="Price">
                      <Price>18.00</Price>
                    </td>
                    <td data-label="Borrow Days">Not Borrowed</td>
                    <td className="table-btn-row">
                      <Button className="btn-secondary">Remove</Button>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <img className="table-image" src={BookImage} alt="" />
                    </td>
                    <td data-label="Name">The Design of Books</td>
                    <td data-label="Author">Debbie Berne</td>
                    <td data-label="Price">
                      <Price className="line-through">18.00</Price>{" "}
                      <Price>3.6</Price>
                    </td>
                    <td data-label="Borrow Days">2</td>
                    <td className="table-btn-row">
                      <Button className="btn-secondary">Remove</Button>
                    </td>
                  </tr>
                </tbody>
              </Table>
            </ExpandableRow>
          );
        })}
      </div>
    </Card>
  );
};

export default HistoryCard;
