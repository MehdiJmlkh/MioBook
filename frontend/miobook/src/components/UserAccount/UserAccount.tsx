import { HiOutlineUserCircle } from "react-icons/hi2";
import "./UserAccount.css";
import { LuMail } from "react-icons/lu";
import Button from "../Button";
import { useLogout } from "../../queries/useLogout";

interface Props {
  className?: string;
}

const UserAccount = ({ className }: Props) => {
  const logout = useLogout();

  return (
    <div className={`user-account ${className}`}>
      <div className="user-account__username">
        <HiOutlineUserCircle className="user-account__icon" />{" "}
        <span>SanaNavaei </span>
      </div>
      <div className="user-account__email">
        <LuMail className="user-account__icon" />{" "}
        <span>sana.sarinavaei@gmail.com</span>
      </div>
      <Button
        className="btn-secondary user-account__btn"
        onClick={() => logout.mutate()}
      >
        Logout
      </Button>
    </div>
  );
};

export default UserAccount;
