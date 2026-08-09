import "./Divider.css";

interface Props {
  children: string;
}

const Divider = ({ children }: Props) => {
  return (
    <div className="divider">
      <span className="divider__line"></span>
      <p className="divider__text">{children}</p>
      <span className="divider__line"></span>
    </div>
  );
};

export default Divider;
