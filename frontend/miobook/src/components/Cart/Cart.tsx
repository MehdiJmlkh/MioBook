import Button from "../Button";
import Table from "../Table";
import { LuShoppingCart } from "react-icons/lu";
import BookImage from "../../assets/book.svg";

import "./Cart.css";
import Price from "../Price";

const Cart = () => {
  return (
    <div className="cart">
      <div className="cart__heading">
        <LuShoppingCart className="cart__icon" />
        <h2 className="cart__title">Cart</h2>
      </div>
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
              <Price className="line-through">18.00</Price> <Price>3.6</Price>
            </td>
            <td data-label="Borrow Days">2</td>
            <td className="table-btn-row">
              <Button className="btn-secondary">Remove</Button>
            </td>
          </tr>
        </tbody>
      </Table>
      <div className="cart__btn">
        <Button className="btn-primary">Purchase</Button>
      </div>
    </div>
  );
};

export default Cart;
