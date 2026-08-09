import "./CheckBox.css";

interface Props {
  className?: string;
  onClick: () => void;
}

const CheckBox = ({ className, onClick }: Props) => {
  return (
    <div className={`form-check ${className}`}>
      <input
        className="form-check-input"
        type="checkbox"
        id="check"
        onClick={onClick}
      />
      <label className="form-check-label" htmlFor="check">
        Borrow this book
      </label>
    </div>
  );
};

export default CheckBox;
