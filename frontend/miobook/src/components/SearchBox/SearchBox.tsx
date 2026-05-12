import "./SearchBox.css";
import FilterSelector from "../FilterSelector";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

interface Props {
  className?: string;
}

const SearchBox = ({ className }: Props) => {
  const filters = ["Author", "Name", "Genre"];

  const { register, handleSubmit } = useForm();
  const navigate = useNavigate();

  const [selectedFilter, setSelectedFilter] = useState(filters[0]);
  return (
    <form
      className={`search-box ${className}`}
      onSubmit={handleSubmit((data) => {
        const params = new URLSearchParams({
          [selectedFilter.toLowerCase()]: data.search,
        });
        navigate({ pathname: "/books", search: params.toString() });
      })}
    >
      <FilterSelector
        filters={filters}
        selectedFilter={selectedFilter}
        onClickFilter={(filter) => setSelectedFilter(filter)}
      />
      <input
        {...register("search")}
        type="text"
        className="search-box__search-text"
        placeholder="Search"
      />
    </form>
  );
};

export default SearchBox;
