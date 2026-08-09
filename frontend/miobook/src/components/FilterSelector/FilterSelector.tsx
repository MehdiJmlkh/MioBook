import "./FilterSelector.css";
import { BsChevronDown } from "react-icons/bs";
import { useState } from "react";

interface Props {
  filters: string[];
  selectedFilter: string;
  onClickFilter: (filter: string) => void;
}

const FilterSelector = ({ filters, selectedFilter, onClickFilter }: Props) => {
  const [open, setOpen] = useState(false);

  const handleSelect = (option: string) => {
    onClickFilter(option);
    setOpen(false);
  };

  return (
    <div
      className={`filter-selector ${open ? "open" : ""}`}
      onMouseEnter={() => setOpen(true)}
      onMouseLeave={() => setOpen(false)}
    >
      <span>{selectedFilter} </span>{" "}
      <BsChevronDown
        className={`filter-selector__icon  ${open ? "open" : ""}`}
      />
      <ul className="filter-selector__options">
        {filters.map((option) => (
          <li
            onClick={() => handleSelect(option)}
            className="filter-selector__option"
          >
            {option}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FilterSelector;
