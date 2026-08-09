import "./Avatar.css";

interface Props {
  username?: string;
  onClick?: () => void;
}

const Avatar = ({ username, onClick }: Props) => {
  return (
    <span onClick={onClick} className="avatar">
      {username?.charAt(0).toLocaleUpperCase()}
    </span>
  );
};

export default Avatar;
