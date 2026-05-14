import { useEffect, useState } from "react";
import BorrowDays from "../BorrowDays";
import Button from "../Button";
import CheckBox from "../CheckBox";
import CloseIcon from "../CloseIcon";
import Price from "../Price";
import "./AddToCartModal.css";
import { useAddItemToCart } from "../../queries/carts/useAddItemToCart";
import { useAddBorrowedItemToCart } from "../../queries/carts/useAddBorrowedItemToCart";
import { useAuth } from "../../queries/useAuth";

interface Props {
  className?: string;
  onClose: () => void;
  price?: number;
  bookTitle?: string;
}

const AddToCartModal = ({
  className,
  onClose,
  price = 0,
  bookTitle,
}: Props) => {
  const [showDays, setShowDays] = useState(false);

  const maxBorrowDays = 10;
  const [borrowDays, setBorrowDays] = useState(maxBorrowDays);

  useEffect(() => {
    setBorrowDays(maxBorrowDays);
  }, [showDays]);

  const addItemToCart = useAddItemToCart();
  const addBorrowedItemToCart = useAddBorrowedItemToCart();

  const { data: user } = useAuth();

  const handleAddToCart = () => {
    if (borrowDays === maxBorrowDays) {
      addItemToCart.mutate({ username: user?.username, title: bookTitle });
    } else {
      addBorrowedItemToCart.mutate({
        username: user?.username,
        title: bookTitle,
        days: borrowDays,
      });
    }
    onClose();
  };

  return (
    <div className={`add-to-cart modal-pop ${className}`}>
      <CloseIcon onClose={onClose} />
      <h1 className="add-to-cart__heading">Add to Cart</h1>
      <p className="add-to-cart__description">
        Would you like to buy or borrow this book?
      </p>
      <CheckBox
        className="add-to-cart__checkbox"
        onClick={() => setShowDays(!showDays)}
      />
      <BorrowDays
        className={showDays ? "borrow-days--visible" : ""}
        onSelectDay={(days) => setBorrowDays(days)}
        selectedDay={borrowDays}
      />
      <div className="add-to-cart__footer">
        <span>
          <span>Final Price:</span>
          <Price className="add-to-cart__price">
            {(price * (borrowDays / maxBorrowDays)).toFixed(2)}
          </Price>
        </span>
        <Button className="btn-primary" onClick={handleAddToCart}>
          Add
        </Button>
      </div>
    </div>
  );
};

export default AddToCartModal;
