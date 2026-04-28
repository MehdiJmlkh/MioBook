import "./Pagination.css";
import { IoChevronBack, IoChevronForward } from "react-icons/io5"; // Ionicons

const Pagination = () => {
  return (
    <div className="pagination">
      <IoChevronBack className="pagination__icon" />
      <span className="page-number page-number--current-page">1</span>
      <span className="page-number">2</span>
      <span className="page-number">3</span>
      <span className="page-number">4</span>
      <span className="page-number">5</span>
      <IoChevronForward className="pagination__icon"/>
    </div>
  );
};

export default Pagination;
