import { useState } from "react";
import Button from "../Button";
import CloseIcon from "../CloseIcon";
import Input from "../Input";
import "./AddBookModal.css";
import TextArea from "../TextArea";

interface Props {
  className?: string;
  onClose: () => void;
}

const AddBookModal = ({ className, onClose }: Props) => {
  const [isSecondPage, setIsSecondPage] = useState(false);
  return (
    <div className={`add-book modal-pop ${className}`}>
      <CloseIcon onClose={onClose} />
      <h1 className="add-book__title">Add Book</h1>
      <div className="add-book__inputs">
        {!isSecondPage && (
          <>
            <Input placeholder="Name" />
            <Input placeholder="Author" />
            <Input placeholder="Publisher" />
            <Input placeholder="Genres" />
            <Input placeholder="Published Year" />
            <Input placeholder="Price" />
            <Input placeholder="Image Link" />
          </>
        )}
        {isSecondPage && (
          <>
            <TextArea
              className="textarea-primary"
              placeholder="Synopsis"
              rows={6}
            />
            <TextArea
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
            <Button className="btn-secondary">Cancel</Button>
          </>
        )}
        {isSecondPage && (
          <>
            <Button className="btn-primary">Submit</Button>
            <Button
              className="btn-secondary"
              onClick={() => setIsSecondPage(false)}
            >
              Back
            </Button>
          </>
        )}
      </div>
    </div>
  );
};

export default AddBookModal;
