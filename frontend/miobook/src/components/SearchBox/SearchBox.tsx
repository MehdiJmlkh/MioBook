import "./SearchBox.css";
import FilterSelector from "../FilterSelector";
import { useState } from "react";

interface Props {
  className?: string;
}

const SearchBox = ({ className }: Props) => {
  const filters = ["Author", "Name", "Genre"];

  const [selectedFilter, setSelectedFilter] = useState(filters[0]);
  return (
    <div className={`search-box ${className}`}>
      <FilterSelector
        filters={filters}
        selectedFilter={selectedFilter}
        onClickFilter={(filter) => setSelectedFilter(filter)}
      />
      <input
        type="text"
        className="search-box__search-text"
        placeholder="Search"
      />
    </div>
  );
};

export default SearchBox;
