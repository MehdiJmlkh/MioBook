import { RiFilter2Line } from "react-icons/ri";
import { useState } from "react";
import Button from "../../components/Button";
import Header from "../../components/Header";
import BookCard from "../../components/BookCard";
import Footer from "../../components/Footer";
import Grid from "../../components/Grid";
import Pagination from "../../components/Pagination";
import FilterSideBar from "../../components/FilterSideBar";
import Backdrop from "../../components/Backdrop";
import { useNoScroll } from "../../hooks/useNoScroll";
import { SearchQuery, useBooks } from "../../queries/useBooks";

import "./SearchResultPage.css";

const SearchResultPage = () => {
  const [page, setPage] = useState(1);
  const [searchParams, setSearchParams] = useState<SearchQuery>({
    page: page,
    size: 10,
  } as SearchQuery);

  const { data: books } = useBooks(searchParams);

  const [showSidebar, setShowSidebar] = useState(false);
  useNoScroll([showSidebar]);

  return (
    <body>
      <Backdrop enabled={showSidebar} />
      <FilterSideBar
        onSubmit={(data) => {
          console.log(data);
          setSearchParams(data);
        }}
        className={showSidebar ? "open" : ""}
        onClose={() => setShowSidebar(false)}
      />
      <Header />
      <main className="search-result__main">
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
          {books?.map((book) => (
            <BookCard book={book} />
          ))}
        </Grid>
      </main>
      <Pagination
        totalPages={5}
        pageNumber={page}
        onClick={(page) => setPage(page)}
      />
      <Footer />
    </body>
  );
};

export default SearchResultPage;
