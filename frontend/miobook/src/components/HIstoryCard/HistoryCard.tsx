import { RiHistoryLine } from "react-icons/ri";
import BookImage from "../../assets/book.svg";
import { usePurchases } from "../../queries/usePurchases";
import Button from "../Button";
import Card from "../Card";
import ExpandableRow from "../ExpandableRow";
import Price from "../Price";
import Table from "../Table";
import "./HistoryCard.css";

const HistoryCard = () => {
  const { data: history } = usePurchases("li_wei");
  return (
    <Card title="History" icon={<RiHistoryLine />}>
      <div className="history-table">
        {history?.purchaseHistory.map((purchase, i) => (
          <ExpandableRow
            key={i}
            title={
              <span>
                {purchase.purchaseDate} | <Price>{purchase.totalCost}</Price>
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
                {purchase.items.map((item, i) => (
                  <tr>
                    <td className="table-image-row">
                      <img className="table-image" src={BookImage} alt="" />
                    </td>
                    <td data-label="Name">{item.title}</td>
                    <td data-label="Author">{item.author}</td>
                    <td data-label="Price">
                      {item.isBorrowed && (
                        <Price className="line-through">{item.price}</Price>
                      )}
                      <Price>{item.finalPrice}</Price>
                    </td>
                    <td data-label="Borrow Days">
                      {item.isBorrowed ? item.borrowDays : "Not Borrowed"}
                    </td>
                    <td className="table-btn-row">
                      <Button className="btn-secondary">Remove</Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </ExpandableRow>
        ))}
      </div>
    </Card>
  );
};

export default HistoryCard;
