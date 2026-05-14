import { useState } from "react";
import { useForm } from "react-hook-form";
import { useAddBook } from "../../queries/books/useAddBook";
import { AddBookRequest } from "../../services/bookService";
import Button from "../Button";
import CloseIcon from "../CloseIcon";
import Input from "../Input";
import TextArea from "../TextArea";
import "./AddBookModal.css";

interface Props {
  className?: string;
  onClose: () => void;
}

const AddBookModal = ({ className, onClose }: Props) => {
  const [page, setPage] = useState<1 | 2>(1);

  const { register, reset, handleSubmit } = useForm<AddBookRequest>();

  const setGenresAs = (value: string) => {
    if (typeof value !== "string") return value;
    return value
      .split(",")
      .map((v) => v.trim())
      .filter(Boolean);
  };

  const onSuccess = () => {
    reset();
    setPage(1);
    onClose();
  };

  const addBook = useAddBook({ onSuccess });

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
              {...register("year")}
              type="number"
              placeholder="Published Year"
            />
            <Input {...register("price")} type="number" placeholder="Price" />
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
              className="btn-secondary"
              onClick={() => reset}
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
