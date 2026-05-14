import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { SearchQuery } from "../../queries/books/useFilteredBooks";
import { useGenres } from "../../queries/books/useGenres";
import Button from "../Button";
import CloseIcon from "../CloseIcon";
import Input from "../Input";
import RadioInput from "../RadioInput";
import RadioOptions from "../RadioOptions";
import SelectInput from "../SelectInput";
import "./FilterSideBar.css";

interface Props {
  onClose: () => void;
  className?: string;
}

const FilterSideBar = ({ onClose, className }: Props) => {
  const { data: genres } = useGenres();

  const { register, handleSubmit } = useForm<SearchQuery>();
  const [selectedGenre, setSelectedGenre] = useState("");

  const navigate = useNavigate();

  return (
    <form
      className={`filter-sidebar ${className}`}
      onSubmit={handleSubmit((data) => {
        const params = new URLSearchParams();

        Object.entries({ ...data, genre: selectedGenre }).forEach(
          ([key, value]) => {
            if (value !== undefined && value !== null && value !== "") {
              params.set(key, String(value));
            }
          },
        );

        navigate({ pathname: "/books", search: params.toString() });
        onClose();
      })}
    >
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
              onChange={(option) => setSelectedGenre(option)}
              className="filter-sidebar__filter__input"
              options={genres}
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
        <Button className="btn-primary">Apply</Button>
      </div>
    </form>
  );
};

export default FilterSideBar;
