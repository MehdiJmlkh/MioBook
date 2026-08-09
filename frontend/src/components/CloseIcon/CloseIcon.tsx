import "./CloseIcon.css";
import { MdClose } from "react-icons/md";

interface Props {
  onClose?: () => void;
}

const CloseIcon = ({ onClose }: Props) => {
  return <MdClose className="close-icon" onClick={onClose} />;
};

export default CloseIcon;
