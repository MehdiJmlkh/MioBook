import "./ImageWithBadge.css";
import noImage from "../../assets/no-image.svg";

interface Props {
  className?: string;
}

const ImageWithBadge = ({ className }: Props) => {
  return (
    <div className={`image ${className}`}>
      <img src={noImage} alt="book's image" />
      <div className="image-backdrop">
        <span className="image-badge">Owned</span>
      </div>
    </div>
  );
};

export default ImageWithBadge;
