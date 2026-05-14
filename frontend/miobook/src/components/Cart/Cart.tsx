import { LuShoppingCart } from "react-icons/lu";
import { useCart } from "../../queries/carts/useCart";
import BookItemRow from "../BookItemRow";
import Button from "../Button";
import Card from "../Card";
import Table from "../Table";
import "./Cart.css";
import { useRemoveCartItem } from "../../queries/carts/useRemoveCartItem";
import { useAuth } from "../../queries/auth/useAuth";
import NoProduct from "../../assets/no-product.svg";

const Cart = () => {
  const { data: user } = useAuth();
  if (!user) {
    return <p>Loading...</p>;
  }
  const { data: cart } = useCart(user.username);
  const removeCartItem = useRemoveCartItem(user.username);

  return (
    <Card title="Cart" className="cart" icon={<LuShoppingCart />}>
      {cart?.items.length === 0 ? (
        <div className="empty-list__content">
          <img className="my-book__no-result" src={NoProduct} alt="" />
          <p>No Product</p>
        </div>      ) : (
        <>
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
              {cart?.items.map((item, i) => (
                <BookItemRow
                  key={i}
                  item={item}
                  addBtn={true}
                  onClick={() => removeCartItem.mutate(item.bookId)}
                />
              ))}
            </tbody>
          </Table>

          <div className="cart__btn">
            <Button className="btn-primary">Purchase</Button>
          </div>
        </>
      )}
    </Card>
  );
};

export default Cart;
