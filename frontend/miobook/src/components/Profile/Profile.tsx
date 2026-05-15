import { useState } from "react";
import { HiOutlineUserCircle } from "react-icons/hi2";
import { LuLogOut, LuShoppingCart } from "react-icons/lu";
import { MdOutlineArticle } from "react-icons/md";
import { RiHistoryLine } from "react-icons/ri";
import { useAuth } from "../../queries/auth/useAuth";
import { useLogout } from "../../queries/auth/useLogout";
import Avatar from "../Avatar";
import Link from "../Link";
import "./Profile.css";

const Profile = () => {
  const [visible, setVisible] = useState(false);
  const { data: user } = useAuth();

  const logout = useLogout();

  return (
    <div className="profile">
      <Avatar onClick={() => setVisible(!visible)} />
      {visible && (
        <div className="profile-menu">
          <div className="profile-menu__header">
            {user?.username.toLocaleUpperCase()}
          </div>
          <Link
            to={user?.role === "ADMIN" ? "/admin" : "/user"}
            className="profile-menu__item"
          >
            <HiOutlineUserCircle className="profile-menu__icon" />
            Profile
          </Link>
          <Link to="/user" className="profile-menu__item">
            <MdOutlineArticle className="profile-menu__icon" />
            My Books
          </Link>
          <Link to="/user/cart" className="profile-menu__item">
            <LuShoppingCart className="profile-menu__icon" />
            Buy Cart
          </Link>
          <Link to="/user/history" className="profile-menu__item">
            <RiHistoryLine className="profile-menu__icon" />
            Purchase History
          </Link>
          <div className="profile-menu__footer" onClick={() => logout.mutate()}>
            <LuLogOut className="profile-menu__icon" />
            Logout
          </div>
        </div>
      )}
    </div>
  );
};

export default Profile;
