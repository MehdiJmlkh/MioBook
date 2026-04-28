import Button from "../Button";
import Input from "../Input";
import RadioInput from "../RadioInput";
import RadioOptions from "../RadioOptions";
import SelectInput from "../SelectInput";
import "./FilterSideBar.css";
import { MdClose } from "react-icons/md";

interface Props {
  onClose: () => void;
}

const FilterSideBar = ({ onClose }: Props) => {
  return (
    <div className="filter-sidebar">
      <MdClose className="filter-sidebar__close-icon" onClick={onClose} />
      <div>
        <h1 className="filter-sidebar__title">Filters</h1>
        <div>
          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Book Name:</h2>
            <Input className="filter-sidebar__filter__input" />
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Author Name:</h2>
            <Input className="filter-sidebar__filter__input" />
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Genre:</h2>
            <SelectInput
              className="filter-sidebar__filter__input"
              options={["Genre 1", "Genre 2", "Genre 3"]}
            />
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Published Year:</h2>
            <Input className="filter-sidebar__filter__input" type="number" />
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Sort By:</h2>
            <RadioOptions>
              <RadioInput name="sort" value="Rating" defaultChecked={true} />
              <RadioInput name="sort" value="Reviews" />
            </RadioOptions>
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Order:</h2>
            <RadioOptions>
              <RadioInput name="order" value="Descending" />
              <RadioInput
                name="order"
                value="Ascending"
                defaultChecked={true}
              />
            </RadioOptions>
          </div>
        </div>
      </div>
      <div className="filter-sidebar__btn">
        <Button className="btn-primary">Apply</Button>
      </div>
    </div>
  );
};

export default FilterSideBar;
