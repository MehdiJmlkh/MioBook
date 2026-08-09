import { useEffect, useState } from "react";
import { RiFilter2Line } from "react-icons/ri";
import Backdrop from "../../components/Backdrop";
import BookCard from "../../components/BookCard";
import Button from "../../components/Button";
import FilterSideBar from "../../components/FilterSideBar";
import Grid from "../../components/Grid";
import Pagination from "../../components/Pagination";
import { useNoScroll } from "../../hooks/useNoScroll";
import {
  SearchQuery,
  useFilteredBooks,
} from "../../queries/books/useFilteredBooks";
import "./SearchResultPage.css";
import { useSearchParams } from "react-router-dom";

const SearchResultPage = () => {
  const [page, setPage] = useState(1);

  const [searchParams] = useSearchParams();

  const pageSize = 10;

  useEffect(() => {
    setPage(1);
  }, [searchParams]);

  const { data: bookPage } = useFilteredBooks({
    ...Object.fromEntries(searchParams),
    page: page,
    size: pageSize,
  } as SearchQuery);

  const [showSidebar, setShowSidebar] = useState(false);
  useNoScroll([showSidebar]);

  return (
    <div>
      <Backdrop enabled={showSidebar} />
      <FilterSideBar
        className={showSidebar ? "open" : ""}
        onClose={() => setShowSidebar(false)}
      />
      <div className="page-container search-result__main ">
        <div className="search-result__heading">
          <h1 className="search-result__title">
            Results for &lt;Search Parameters&gt;{" "}
          </h1>
          <Button
            onClick={() => setShowSidebar(true)}
            className="btn-primary search-result__btn"
          >
            <RiFilter2Line /> Filter
          </Button>
        </div>
        <Grid>
          {bookPage?.books.map((book) => (
            <BookCard book={book} />
          ))}
        </Grid>
      </div>
      <Pagination
        totalPages={Math.ceil((bookPage?.totalBooks || 1) / pageSize)}
        pageNumber={page}
        onClick={(page) => setPage(page)}
      />
    </div>
  );
};

export default SearchResultPage;
