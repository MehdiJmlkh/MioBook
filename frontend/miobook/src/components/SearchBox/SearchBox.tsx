import { HiChevronDown } from "react-icons/hi";

import "./SearchBox.css";
import FilterSelector from "../FilterSelector";
import { useState } from "react";

const SearchBox = () => {
  const filters = ["Author", "Name", "Genre"];

  const [selectedFilter, setSelectedFilter] = useState(filters[0]);
  return (
    <>
      <div className="search-box">
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
    </>
  );
};

export default SearchBox;
