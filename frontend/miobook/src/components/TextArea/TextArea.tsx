import "../Input/Input.css";
import "./TextArea.css";

interface Props {
  className?: string;
  rows: number;
}

const TextArea = ({ className, rows }: Props) => {
  return (
    <textarea
      className={`form-control form-control--dark-background ${className}`}
      id="description"
      rows={rows}
      placeholder="Type your review..."
    ></textarea>
  );
};

export default TextArea;
