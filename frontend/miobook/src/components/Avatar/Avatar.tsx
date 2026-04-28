import "./Avatar.css";

interface Props {
  onClick?: () => void;
}

const Avatar = ({ onClick }: Props) => {
  return (
    <span onClick={onClick} className="avatar">
      S
    </span>
  );
};

export default Avatar;
