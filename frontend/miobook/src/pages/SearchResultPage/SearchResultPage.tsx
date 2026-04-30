import Button from "../../components/Button";
import Header from "../../components/Header";
import { RiFilter2Line } from "react-icons/ri";
import "./SearchResultPage.css";
import BookCard from "../../components/BookCard";
import Footer from "../../components/Footer";
import Grid from "../../components/Grid";
import Pagination from "../../components/Pagination";
import { useEffect, useState } from "react";
import FilterSideBar from "../../components/FilterSideBar";
import Backdrop from "../../components/Backdrop";
import { useNoScroll } from "../../hooks/useNoScroll";

const SearchResultPage = () => {
  const [pageNumber, setPageNumber] = useState(1);
  const [openSideBar, setOpenSideBar] = useState(false);

  useNoScroll([openSideBar]);

  return (
    <body>
      <Backdrop enabled={openSideBar} />
      <FilterSideBar
        className={openSideBar ? "open" : ""}
        onClose={() => setOpenSideBar(false)}
      />
      <Header />
      <main className="search-result__main">
        <div className="search-result__heading">
          <h1 className="search-result__title">
            Results for &lt;Search Parameters&gt;{" "}
          </h1>
          <Button
            onClick={() => setOpenSideBar(true)}
            className="btn-primary search-result__btn"
          >
            <RiFilter2Line /> Filter
          </Button>
        </div>
        <Grid>
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
          <BookCard />
        </Grid>
      </main>
      <Pagination
        totalPages={5}
        pageNumber={pageNumber}
        onClick={(page) => setPageNumber(page)}
      />
      <Footer />
    </body>
  );
};

export default SearchResultPage;
