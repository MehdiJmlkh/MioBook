import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useAddBook } from "../../queries/books/useAddBook";
import { AddBookRequest } from "../../services/bookService";
import Button from "../Button";
import CloseIcon from "../CloseIcon";
import Input from "../Input";
import TextArea from "../TextArea";
import "./AddBookModal.css";
import { useAuth } from "../../queries/auth/useAuth";

interface Props {
  className?: string;
  onClose: () => void;
}

const AddBookModal = ({ className, onClose }: Props) => {
  const [page, setPage] = useState<1 | 2>(1);

  const { data: user } = useAuth();

  if (!user) {
    return <p>Loading...</p>;
  }

  const {
    register,
    reset,
    handleSubmit,
    formState: { isValid },
  } = useForm<AddBookRequest>();

  const setGenresAs = (value: string) => {
    if (typeof value !== "string") return value;
    return value
      .split(",")
      .map((v) => v.trim())
      .filter(Boolean);
  };

  const addBook = useAddBook();

  const requiredRegister = (name: keyof AddBookRequest) =>
    register(name, { required: true });

  useEffect(() => {
    setPage(1);
  }, [addBook.error]);

  return (
    <form className={`add-book modal-pop ${className}`}>
      <CloseIcon onClose={onClose} />
      <h1 className="add-book__title">Add Book</h1>
      <div className="add-book__inputs">
        {page === 1 ? (
          <>
            <Input
              {...requiredRegister("title")}
              placeholder="Name"
              error={addBook.error?.title}
            />
            <Input
              {...requiredRegister("author")}
              placeholder="Author"
              error={addBook.error?.author}
            />
            <Input {...requiredRegister("publisher")} placeholder="Publisher" />
            <Input
              {...register("genres", {
                setValueAs: setGenresAs,
                required: true,
              })}
              placeholder="Genres"
            />
            <Input
              {...requiredRegister("year")}
              type="number"
              placeholder="Published Year"
            />
            <Input
              {...requiredRegister("price")}
              type="number"
              placeholder="Price"
            />
            <Input
              {...requiredRegister("imageLink")}
              placeholder="Image Link"
            />
          </>
        ) : (
          <>
            <TextArea
              {...requiredRegister("synopsis")}
              className="textarea-primary"
              placeholder="Synopsis"
              rows={6}
            />
            <TextArea
              {...requiredRegister("content")}
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
              disabled={!isValid}
            >
              Next
            </Button>
            <Button
              key={"reset"}
              type="reset"
              className="btn-secondary"
              onClick={() => reset()}
            >
              Cancel
            </Button>
          </>
        ) : (
          <>
            <Button
              key={"submit"}
              className="btn-primary"
              onClick={handleSubmit((data) =>
                addBook.mutate(
                  { ...data, username: user?.username },
                  {
                    onSuccess: () => {
                      reset();
                      setPage(1);
                      onClose();
                    },
                  },
                ),
              )}
              disabled={!isValid}
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
