import Button from "../Button";
import CloseIcon from "../CloseIcon";
import DateInput from "../DateInput";
import Input from "../Input";
import "./AddAuthorModal.css";

const AddAuthorModal = () => {
  return (
    <div className="add-author">
      <CloseIcon />
      <h1 className="add-author__title">Add Author</h1>
      <div className="add-author__inputs">
        <Input placeholder="Name" />
        <Input placeholder="Pen Name" />
        <Input placeholder="Nationality" />
        <DateInput placeholder="Born" />
        <DateInput placeholder="Dided" />
        <Input placeholder="Image Link" />
      </div>
      <div className="add-author__btns">
        <Button className="btn-primary">Submit</Button>
        <Button className="btn-secondary">Cancel</Button>
      </div>
    </div>
  );
};

export default AddAuthorModal;
