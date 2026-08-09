import Avatar from "../Avatar";
import MeowIcon from "../MeowLogo/MeowLogo";
import Profile from "../Profile";
import SearchBox from "../SearchBox";
import "./Header.css";

interface Props {
  className?: string;
}

const Header = ({ className }: Props) => {
  return (
    <header className={`header ${className}`}>
      <MeowIcon />
      <SearchBox className="search-box" />
      <Profile />
    </header>
  );
};

export default Header;
