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
  const [page, setPage] = useState<1 | 2>(1);

  const { register, reset, handleSubmit } = useForm<AddBookRequest>();

  const onSuccess = () => {
    reset();
    setPage(1);
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

  return (
    <form className={`add-book modal-pop ${className}`}>
      <CloseIcon onClose={onClose} />
      <h1 className="add-book__title">Add Book</h1>
      <div className="add-book__inputs">
        {page === 1 ? (
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
        ) : (
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
        {page === 1 ? (
          <>
            <Button
              key={"next"}
              type="button"
              className="btn-primary"
              onClick={() => setPage(2)}
            >
              Next
            </Button>
            <Button
              key={"reset"}
              type="reset"
              onClick={() => reset}
              className="btn-secondary"
            >
              Cancel
            </Button>
          </>
        ) : (
          <>
            <Button
              key={"submit"}
              className="btn-primary"
              onClick={handleSubmit((data) => addBook.mutate(data))}
            >
              Submit
            </Button>
            <Button
              key={"back"}
              className="btn-secondary"
              onClick={() => setPage(1)}
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
