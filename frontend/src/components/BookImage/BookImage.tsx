import "./BookImage.css";

interface Props {
  imageLink?: string;
  className?: string;
}

const BookImage = ({ imageLink, className }: Props) => {
  const baseUrl = window.location.origin;

  return (
    <img
      className={className}
      src={`${baseUrl}/public/${imageLink || "no-image.svg"}`}
      alt="Image of the book"
    />
  );
};

export default BookImage;
