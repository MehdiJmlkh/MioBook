import { RiHistoryLine } from "react-icons/ri";
import BookImage from "../../assets/book.svg";
import { usePurchases } from "../../queries/purchases/usePurchases";
import Button from "../Button";
import Card from "../Card";
import ExpandableRow from "../ExpandableRow";
import Price from "../Price";
import Table from "../Table";
import "./HistoryCard.css";
import BookItemRow from "../BookItemRow";
import { useAuth } from "../../queries/auth/useAuth";
import EmptyIcon from "../EmptyIcon";
import NoResult from "../../assets/no-result.svg";

const HistoryCard = () => {
  const { data: user } = useAuth();
  if (!user) {
    return <p>Loading...</p>;
  }

  const { data: history } = usePurchases(user.username);

  return (
    <Card title="History" icon={<RiHistoryLine />}>
      {!history || history?.purchaseHistory.length === 0 ? (
        <EmptyIcon src={NoResult} description="No Result" />
      ) : (
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
      )}
    </Card>
  );
};

export default HistoryCard;
