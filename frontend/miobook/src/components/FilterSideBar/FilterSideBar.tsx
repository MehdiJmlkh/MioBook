import Button from "../Button";
import CloseIcon from "../CloseIcon";
import Input from "../Input";
import RadioInput from "../RadioInput";
import RadioOptions from "../RadioOptions";
import SelectInput from "../SelectInput";
import "./FilterSideBar.css";
import { useForm } from "react-hook-form";
import { SearchQuery } from "../../queries/useBooks";
import { useState } from "react";

interface Props {
  onClose: () => void;
  className?: string;
  onSubmit: (data: SearchQuery) => void;
}

const FilterSideBar = ({ onClose, className, onSubmit }: Props) => {
  const { register, handleSubmit } = useForm<SearchQuery>();
  const [genre, setGenre] = useState("");

  return (
    <div className={`filter-sidebar ${className}`}>
      <CloseIcon onClose={onClose} />
      <div>
        <h1 className="filter-sidebar__title">Filters</h1>
        <div>
          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Book Name:</h2>
            <Input
              className="filter-sidebar__filter__input"
              {...register("title")}
            />
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Author Name:</h2>
            <Input
              className="filter-sidebar__filter__input"
              {...register("author")}
            />
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Genre:</h2>
            <SelectInput
              onChange={(option) => setGenre(option)}
              className="filter-sidebar__filter__input"
              options={["Genre 1", "Genre 2", "Genre 3"]}
            />
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Published Year:</h2>
            <Input
              className="filter-sidebar__filter__input"
              type="number"
              {...register("year")}
            />
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Sort By:</h2>
            <RadioOptions>
              <RadioInput
                value="Rating"
                defaultChecked={true}
                {...register("sortBy")}
              />
              <RadioInput value="Reviews" {...register("sortBy")} />
            </RadioOptions>
          </div>

          <div className="filter-sidebar__filter">
            <h2 className="filter-sidebar__filter__name">Order:</h2>
            <RadioOptions>
              <RadioInput value="Descending" {...register("order")} />
              <RadioInput
                value="Ascending"
                {...register("order")}
                defaultChecked={true}
              />
            </RadioOptions>
          </div>
        </div>
      </div>
      <div className="filter-sidebar__btn">
        <Button
          className="btn-primary"
          onClick={handleSubmit((data) => onSubmit({ ...data, genre: genre }))}
        >
          Apply
        </Button>
      </div>
    </div>
  );
};

export default FilterSideBar;
