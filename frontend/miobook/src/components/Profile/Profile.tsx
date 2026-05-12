import Avatar from "../Avatar";
import "./Profile.css";
import { HiOutlineUserCircle } from "react-icons/hi2";
import { LuShoppingCart } from "react-icons/lu";
import { LuLogOut } from "react-icons/lu";
import { RiHistoryLine } from "react-icons/ri";
import { MdOutlineArticle } from "react-icons/md";
import { useState } from "react";
import Link from "../Link";

const Profile = () => {
  const [visible, setVisible] = useState(false);

  return (
    <div className="profile">
      <Avatar onClick={() => setVisible(!visible)} />
      {visible && (
        <div className="profile-menu">
          <div className="profile-menu__header">Sample Name</div>
          <Link to="/user" className="profile-menu__item">
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
          <div className="profile-menu__footer">
            <LuLogOut className="profile-menu__icon" />
            Logout
          </div>
        </div>
      )}
    </div>
  );
};

export default Profile;
