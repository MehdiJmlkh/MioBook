import "./ImageWithBadge.css";
import noImage from "../../assets/no-image.svg";

interface Props {
  className?: string;
  label?: string;
  src?: string;
}

const ImageWithBadge = ({ className, label, src }: Props) => {
  return (
    <div className={`image ${className}`}>
      <img src={src || noImage} alt="book's image" />
      <div className="image-backdrop">
        <span className="image-badge">{label}</span>
      </div>
    </div>
  );
};

export default ImageWithBadge;
