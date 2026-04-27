import "./FilterSelector.css";
import { BsChevronDown } from "react-icons/bs";
import { useState } from "react";

interface Props {
  filters: string[];
  selectedFilter: string;
  onClickFilter: (filter: string) => void;
}

const FilterSelector = ({ filters, selectedFilter, onClickFilter }: Props) => {
  const [isOpen, setOpen] = useState(false);
  return (
    <div onClick={() => setOpen(!isOpen)} className="filter-selector">
      <span>{selectedFilter} </span> <BsChevronDown className="filter-selector__icon"/>
      
      <ul className="filter-selector__options">
        {isOpen &&
          filters.map((option) => (
            <li
              onClick={() => onClickFilter(option)}
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
