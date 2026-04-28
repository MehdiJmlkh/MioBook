import "./Pagination.css";
import { IoChevronBack, IoChevronForward } from "react-icons/io5";

interface Props {
  pageNumber: number;
  totalPages: number;
  onClick: (page: number) => void;
}

const Pagination = ({ pageNumber, totalPages, onClick }: Props) => {
  const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

  return (
    <div className="pagination">
      <IoChevronBack
        className="pagination__icon"
        onClick={() => onClick(Math.max(pageNumber - 1, 1))}
      />

      {pages.map((page) => (
        <span
          key={page}
          className={`page-number ${
            page === pageNumber ? "page-number--current-page" : ""
          }`}
          onClick={() => onClick(page)}
        >
          {page}
        </span>
      ))}

      <IoChevronForward
        className="pagination__icon"
        onClick={() => onClick(Math.min(pageNumber + 1, totalPages))}
      />
    </div>
  );
};

export default Pagination;
