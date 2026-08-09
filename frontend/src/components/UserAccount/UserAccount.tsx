import { HiOutlineUserCircle } from "react-icons/hi2";
import "./UserAccount.css";
import { LuMail } from "react-icons/lu";
import Button from "../Button";
import { useLogout } from "../../queries/auth/useLogout";
import { useAuth } from "../../queries/auth/useAuth";

interface Props {
  className?: string;
}

const UserAccount = ({ className }: Props) => {
  const {data: user} = useAuth();
  const logout = useLogout();

  return (
    <div className={`user-account ${className}`}>
      <div className="user-account__username">
        <HiOutlineUserCircle className="user-account__icon" />{" "}
        <span>{user?.username}</span>
      </div>
      <div className="user-account__email">
        <LuMail className="user-account__icon" />{" "}
        <span>{user?.email}</span>
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
