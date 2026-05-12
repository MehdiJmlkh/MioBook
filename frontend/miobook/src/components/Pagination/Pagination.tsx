import "./Pagination.css";
import { IoChevronBack, IoChevronForward } from "react-icons/io5";

interface Props {
  pageNumber: number;
  totalPages: number;
  onClick: (page: number) => void;
}

const Pagination = ({ pageNumber, totalPages, onClick }: Props) => {
  const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

  let startIndex = Math.max(pageNumber - 3, 0);
  const endIndex = Math.min(startIndex + 5, pages.length);
  if (endIndex === pages.length) {
    startIndex = Math.max(endIndex - 5, 0);
  }

  return (
    <div className="pagination">
      <IoChevronBack
        className="pagination__icon"
        onClick={() => onClick(Math.max(pageNumber - 1, 1))}
      />

      {pages.slice(startIndex, endIndex).map((page) => (
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
