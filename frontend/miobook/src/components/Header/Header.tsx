import Avatar from "../Avatar";
import MeowIcon from "../MeowLogo/MeowLogo";
import Profile from "../Profile";
import SearchBox from "../SearchBox";
import "./Header.css";

const Header = () => {
  return (
    <header className="header">
      <MeowIcon />
      <SearchBox className="search-box" />
      <Profile />
    </header>
  );
};

export default Header;
