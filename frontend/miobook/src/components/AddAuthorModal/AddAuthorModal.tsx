import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { useAddAuthor } from "../../queries/authors/useAddAuthor";
import Button from "../Button";
import CloseIcon from "../CloseIcon";
import DateInput from "../DateInput";
import Input from "../Input";
import "./AddAuthorModal.css";
import { useAuth } from "../../queries/auth/useAuth";

interface Props {
  className?: string;
  onClose: () => void;
}

const schema = z.object({
  name: z.string().min(1),
  penName: z.string().min(1),
  nationality: z.string().min(1),
  born: z.string().min(1),
  died: z.any(),
  imageLink: z.string().min(1),
});

type FormData = z.infer<typeof schema>;

const AddAuthorModal = ({ className, onClose }: Props) => {
  const {
    control,
    register,
    handleSubmit,
    reset,
    formState: { isValid },
  } = useForm<FormData>({
    resolver: zodResolver(schema),
  });

  const { data: user } = useAuth();
  const addAuthor = useAddAuthor();

  const handleAddAuthor = handleSubmit((data) =>
    addAuthor.mutate(
      { ...data, username: user?.username },
      {
        onSuccess: () => {
          reset();
          onClose();
        },
      },
    ),
  );

  return (
    <form
      className={`add-author modal-pop ${className}`}
      onSubmit={handleAddAuthor}
    >
      <CloseIcon onClose={onClose} />
      <h1 className="add-author__title">Add Author</h1>
      <div className="add-author__inputs">
        <Input
          {...register("name")}
          placeholder="Name"
          error={addAuthor.error?.name}
        />
        <Input {...register("penName")} placeholder="Pen Name" />
        <Input {...register("nationality")} placeholder="Nationality" />
        <DateInput control={control} name="born" placeholder="Born" />
        <DateInput control={control} name="died" placeholder="Dided" />
        <Input {...register("imageLink")} placeholder="Image Link" />
      </div>

      <div className="add-author__btns">
        <Button disabled={!isValid} className="btn-primary">
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
