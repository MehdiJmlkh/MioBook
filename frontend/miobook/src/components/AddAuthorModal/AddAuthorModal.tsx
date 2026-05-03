import { useForm } from "react-hook-form";
import Button from "../Button";
import CloseIcon from "../CloseIcon";
import DateInput from "../DateInput";
import Input from "../Input";
import "./AddAuthorModal.css";
import { Author } from "../../services/authorService";
import { useState } from "react";
import { useAddAuthor } from "../../queries/useAddAuthor";

interface Props {
  className?: string;
  onClose: () => void;
}

const AddAuthorModal = ({ className, onClose }: Props) => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { isValid },
  } = useForm<Author>();

  const [bornDate, setBornDate] = useState<Date | null>(null);
  const [diedDate, setDiedDate] = useState<Date | null>(null);

  const addAuthor = useAddAuthor();

  return (
    <form className={`add-author modal-pop ${className}`}>
      <CloseIcon onClose={onClose} />
      <h1 className="add-author__title">Add Author</h1>
      <div className="add-author__inputs">
        <Input {...register("name", { required: true })} placeholder="Name" />
        <Input
          {...register("penName", { required: true })}
          placeholder="Pen Name"
        />
        <Input
          {...register("nationality", { required: true })}
          placeholder="Nationality"
        />
        <DateInput
          value={bornDate}
          onChange={(date) => setBornDate(date)}
          placeholder="Born"
        />
        <DateInput
          value={diedDate}
          onChange={(date) => setDiedDate(date)}
          placeholder="Dided"
        />
        <Input placeholder="Image Link" />
      </div>
      <div className="add-author__btns">
        <Button
          disabled={!(isValid && bornDate)}
          className="btn-primary"
          onClick={handleSubmit((data) => {
            addAuthor.mutate({
              ...data,
              born: bornDate?.toISOString().slice(0, 10),
              died: diedDate?.toISOString().slice(0, 10),
            });
            reset();
            setBornDate(null);
            setDiedDate(null);
            onClose();
          })}
        >
          Submit
        </Button>
        <Button
          type="reset"
          onClick={() => {
            setBornDate(null);
            setDiedDate(null);
          }}
          className="btn-secondary"
        >
          Cancel
        </Button>
      </div>
    </form>
  );
};

export default AddAuthorModal;
