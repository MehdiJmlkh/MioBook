import { zodResolver } from "@hookform/resolvers/zod";
import { Controller, useForm } from "react-hook-form";
import { z } from "zod";
import NoImage from "../../assets/no-image.svg";
import { useAddReview } from "../../queries/reviews/useAddReview";
import Button from "../Button";
import CloseIcon from "../CloseIcon";
import RatingStars from "../RatingStars";
import TextArea from "../TextArea";
import "./AddReviewModal.css";
import { useAuth } from "../../queries/auth/useAuth";

interface Props {
  className?: string;
  onClose: () => void;
  bookTitle?: string;
  bookId?: number;
}

const schema = z.object({
  rate: z.number().min(1),
  comment: z.string().min(1),
});

type FormData = z.infer<typeof schema>;

const AddReviewModal = ({ className, onClose, bookTitle, bookId }: Props) => {
  const {
    register,
    control,
    handleSubmit,
    reset,
    formState: { isValid },
  } = useForm<FormData>({
    resolver: zodResolver(schema),
  });

  const addReview = useAddReview(bookId);
  const { data: user } = useAuth();

  const handleSubmitReview = handleSubmit((data) => {
    addReview.mutate(
      {
        ...data,
        username: user?.username,
        title: bookTitle,
      },
      { onSuccess: () => onClose() },
    );
  });

  return (
    <form className={`add-review modal-pop ${className}`}>
      <div>
        <CloseIcon onClose={onClose} />
        <h1 className="add-review__heading">Add Review</h1>
        <img src={NoImage} alt="" className="add-review__img" />
        <h2 className="add-review__book-title">{bookTitle}</h2>
        <div className="add-review__rating">
          <h3 className="add-review__rating__heading">Rating</h3>

          <Controller
            name="rate"
            control={control}
            defaultValue={0}
            render={({ field }) => (
              <RatingStars value={field.value} onChange={field.onChange} />
            )}
          />
          <p className="add-review__rating__description">Tap to Rate</p>
        </div>

        <TextArea
          {...register("comment")}
          className="add-review__review"
          rows={7}
          placeholder="Type your review..."
        />
      </div>
      <div>
        <div className="text-danger add-review__error">
          {addReview.error?.error}
        </div>
        <Button
          className="btn-primary"
          disabled={!isValid}
          onClick={handleSubmitReview}
        >
          Submit Reviews
        </Button>

        <Button
          type="reset"
          onClick={() => reset()}
          className="btn-secondary add-review__cancel"
        >
          Cancel
        </Button>
      </div>
    </form>
  );
};

export default AddReviewModal;
