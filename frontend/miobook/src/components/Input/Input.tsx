import "./Input.css";

interface Props {
  placeholder?: string;
}

const Input = ({ placeholder }: Props) => {
  return <input className="form-control" placeholder={placeholder}></input>;
};

export default Input;
