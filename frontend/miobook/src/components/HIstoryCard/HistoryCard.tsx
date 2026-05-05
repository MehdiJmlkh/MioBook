import { RiHistoryLine } from "react-icons/ri";
import BookImage from "../../assets/book.svg";
import { usePurchases } from "../../queries/usePurchases";
import Button from "../Button";
import Card from "../Card";
import ExpandableRow from "../ExpandableRow";
import Price from "../Price";
import Table from "../Table";
import "./HistoryCard.css";
import BookItemRow from "../BookItemRow";

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
                </tr>
              </thead>
              <tbody>
                {purchase.items.map((item, i) => (
                  <BookItemRow key={i} item={item} />
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
