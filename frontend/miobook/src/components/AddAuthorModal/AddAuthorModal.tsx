import { useForm } from "react-hook-form";
import { useAddAuthor } from "../../queries/useAddAuthor";
import { Author } from "../../services/authorService";
import Button from "../Button";
import CloseIcon from "../CloseIcon";
import DateInput from "../DateInput";
import Input from "../Input";
import "./AddAuthorModal.css";

interface Props {
  className?: string;
  onClose: () => void;
}

const AddAuthorModal = ({ className, onClose }: Props) => {
  const {
    control,
    register,
    handleSubmit,
    reset,
    formState: { isValid },
  } = useForm<Author>();

  const onSuccess = () => {
    reset();
    onClose();
  };

  const addAuthor = useAddAuthor({ onSuccess });

  return (
    <form className={`add-author modal-pop ${className}`}>
      <CloseIcon onClose={onClose} />
      <h1 className="add-author__title">Add Author</h1>
      <div className="add-author__inputs">
        <Input
          {...register("name", { required: true })}
          placeholder="Name"
          error={addAuthor.error?.name}
        />
        <Input
          {...register("penName", { required: true })}
          placeholder="Pen Name"
        />
        <Input
          {...register("nationality", { required: true })}
          placeholder="Nationality"
        />
        <DateInput control={control} name="born" placeholder="Born" />
        <DateInput control={control} name="died" placeholder="Dided" />
        <Input placeholder="Image Link" />
      </div>
      <div className="add-author__btns">
        <Button
          disabled={!isValid}
          className="btn-primary"
          onClick={handleSubmit((data) => addAuthor.mutate(data))}
        >
          Submit
        </Button>
        <Button type="reset" onClick={reset} className="btn-secondary">
          Cancel
        </Button>
      </div>
    </form>
  );
};

export default AddAuthorModal;
