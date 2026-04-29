import "./AddReviewCard.css";
import NoImage from "../../assets/no-image.svg";
import Input from "../Input";
import Button from "../Button";
import Star from "../Star";
import RatingStars from "../RatingStars";
import TextArea from "../TextArea";
import CloseIcon from "../CloseIcon";

const AddReviewCard = () => {
  return (
    <div className="add-review">
      <CloseIcon/>
      <h1 className="add-review__heading">Add Review</h1>
      <img src={NoImage} alt="" className="add-review__img" />
      <h2 className="add-review__book-title">Book Title</h2>
      <div className="add-review__rating">
        <h3 className="add-review__rating__heading">Rating</h3>
        <RatingStars />
        <p className="add-review__rating__description">Tap to Rate</p>
      </div>

      <TextArea className="add-review__review" rows={7} />

      <Button className="btn-primary" disabled={false}>
        Submit Reviews
      </Button>

      <Button className="btn-secondary add-review__cancel">Cancel</Button>
    </div>
  );
};

export default AddReviewCard;
