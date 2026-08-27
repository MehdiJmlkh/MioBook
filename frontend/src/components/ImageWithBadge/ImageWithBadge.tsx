import "./ImageWithBadge.css";
import BookImage from "../BookImage";

interface Props {
  className?: string;
  label?: string;
  imageLink?: string;
}

const ImageWithBadge = ({ className, label, imageLink }: Props) => {
  return (
    <div className={`image ${className}`}>
      <BookImage imageLink={imageLink} />
      <div className="image-backdrop">
        <span className="image-badge">{label}</span>
      </div>
    </div>
  );
};

export default ImageWithBadge;
