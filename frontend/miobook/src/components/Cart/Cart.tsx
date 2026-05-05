import Button from "../Button";
import Table from "../Table";
import { LuShoppingCart } from "react-icons/lu";
import BookImage from "../../assets/book.svg";

import "./Cart.css";
import Price from "../Price";
import Card from "../Card";
import { useCart } from "../../queries/useCart";

const Cart = () => {
  const { data: cart } = useCart("li_wei");

  return (
    <Card title="Cart" icon={<LuShoppingCart />}>
      <Table>
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
          {cart?.items.map((item) => (
            <tr>
              <td className="table-image-row">
                <img className="table-image" src={BookImage} alt="" />
              </td>
              <td data-label="Name">{item.title}</td>
              <td data-label="Author">{item.author}</td>
              <td data-label="Price">
                {item.isBorrowed && (
                  <>
                    <Price className="line-through">{item.price}</Price>{" "}
                  </>
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
      <div className="cart__btn">
        <Button className="btn-primary">Purchase</Button>
      </div>
    </Card>
  );
};

export default Cart;
