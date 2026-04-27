import Avatar from "../Avatar";
import MeowIcon from "../MeowLogo/MeowLogo";
import SearchBox from "../SearchBox";
import "./Header.css";

const Header = () => {
  return (
    <header className="header">
      <MeowIcon />
      <SearchBox className="search-box" />
      <Avatar />
    </header>
  );
};

export default Header;
