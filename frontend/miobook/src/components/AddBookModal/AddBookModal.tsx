import { useState } from "react";
import Button from "../Button";
import CloseIcon from "../CloseIcon";
import Input from "../Input";
import "./AddBookModal.css";
import TextArea from "../TextArea";
import { useForm } from "react-hook-form";
import { Book } from "../../services/bookService";
import { useAddBook } from "../../queries/useAddBook";

interface Props {
  className?: string;
  onClose: () => void;
}

export interface AddBookRequest {
  title: string;
  author: string;
  price: number;
  averageRating: number;
  year: number;
  publisher: string;
  genres: string[];
  synopsis: string;
  content: string;
}

const AddBookModal = ({ className, onClose }: Props) => {
  const [isSecondPage, setIsSecondPage] = useState(false);

  const { register, reset, handleSubmit } = useForm<AddBookRequest>();

  const onSuccess = () => {
    reset();
    setIsSecondPage(false);
    onClose();
  };

  const addBook = useAddBook({ onSuccess });

  const setGenresAs = (value: string) => {
    if (typeof value !== "string") return value;
    return value
      .split(",")
      .map((v) => v.trim())
      .filter(Boolean);
  };

  console.log(addBook.error);
  return (
    <form className={`add-book modal-pop ${className}`}>
      <CloseIcon onClose={onClose} />
      <h1 className="add-book__title">Add Book</h1>
      <div className="add-book__inputs">
        {!isSecondPage && (
          <>
            <Input
              {...register("title")}
              placeholder="Name"
              error={addBook.error?.title}
            />
            <Input
              {...register("author")}
              placeholder="Author"
              error={addBook.error?.author}
            />
            <Input {...register("publisher")} placeholder="Publisher" />
            <Input
              {...register("genres", { setValueAs: setGenresAs })}
              placeholder="Genres"
            />
            <Input
              type="number"
              {...register("year")}
              placeholder="Published Year"
            />
            <Input type="number" {...register("price")} placeholder="Price" />
            <Input placeholder="Image Link" />
          </>
        )}
        {isSecondPage && (
          <>
            <TextArea
              {...register("synopsis")}
              className="textarea-primary"
              placeholder="Synopsis"
              rows={6}
            />
            <TextArea
              {...register("content")}
              className="textarea-primary"
              placeholder="Content"
              rows={9}
            />
          </>
        )}
      </div>

      <div className="add-book__btns">
        {!isSecondPage && (
          <>
            <Button
              className="btn-primary"
              onClick={() => setIsSecondPage(true)}
            >
              Next
            </Button>
            <Button
              type="reset"
              onClick={() => reset()}
              className="btn-secondary"
            >
              Cancel
            </Button>
          </>
        )}
        {isSecondPage && (
          <>
            <Button
              className="btn-primary"
              onClick={handleSubmit((data) => addBook.mutate(data))}
            >
              Submit
            </Button>
            <Button
              className="btn-secondary"
              onClick={() => setIsSecondPage(false)}
            >
              Back
            </Button>
          </>
        )}
      </div>
    </form>
  );
};

export default AddBookModal;
