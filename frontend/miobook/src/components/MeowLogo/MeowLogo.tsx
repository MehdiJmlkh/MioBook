import "./MeowLogo.css";
import Logo from "../../assets/logo.png";
import { useNavigate } from "react-router-dom";

const MeowIcon = () => {
  const navigate = useNavigate();
  return (
    <span className="meow-icon" onClick={() => navigate("/")}>
      <img src={Logo} alt="" />
    </span>
  );
};

export default MeowIcon;
