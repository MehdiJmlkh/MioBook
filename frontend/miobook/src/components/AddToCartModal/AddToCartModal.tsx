import { useState } from "react";
import BorrowDays from "../BorrowDays";
import Button from "../Button";
import CheckBox from "../CheckBox";
import CloseIcon from "../CloseIcon";
import Price from "../Price";
import "./AddToCartModal.css";

interface Props {
  className?: string;
  onClose: () => void;
}

const AddToCartModal = ({ className, onClose }: Props) => {
  const [showDays, setShowDays] = useState(false);

  return (
    <div className={`add-to-cart modal-pop ${className}`}>
      <CloseIcon onClose={onClose}/>
      <h1 className="add-to-cart__heading">Add to Cart</h1>
      <p className="add-to-cart__description">
        Would you like to buy or borrow this book?
      </p>
      <CheckBox
        className="add-to-cart__checkbox"
        onClick={() => setShowDays(!showDays)}
      />
      <BorrowDays className={showDays ? "borrow-days--visible" : ""} />
      <div className="add-to-cart__footer">
        <span>
          <span>Final Price:</span>
          <Price className="add-to-cart__price">1.28</Price>
        </span>
        <Button className="btn-primary">Add</Button>
      </div>
    </div>
  );
};

export default AddToCartModal;
