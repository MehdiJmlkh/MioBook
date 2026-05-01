import "../Input/Input.css";
import "./TextArea.css";

interface Props {
  className?: string;
  rows: number;
  placeholder: string;
}

const TextArea = ({ className, rows, placeholder }: Props) => {
  return (
    <textarea
      className={`form-control form-control--dark-background ${className}`}
      id="description"
      rows={rows}
      placeholder={placeholder}
    ></textarea>
  );
};

export default TextArea;
