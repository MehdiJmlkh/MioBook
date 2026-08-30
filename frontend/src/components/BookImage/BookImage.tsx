import "./BookImage.css";
import defaultNoImage from "../../assets/no-image.svg";

interface Props {
  imageLink?: string;
  className?: string;
  noImage?: string;
}

const BookImage = ({
  imageLink,
  className,
  noImage = defaultNoImage,
}: Props) => {
  const imageSrc = imageLink ? imageLink : noImage;

  return (
    <img
      className={className}
      src={imageSrc}
      alt="Image of the book"
      onError={(error) => {
        error.currentTarget.onerror = null;
        error.currentTarget.src = noImage;
      }}
    />
  );
};

export default BookImage;
