import BookCard from "../../components/BookCard";
import Footer from "../../components/Footer";
import Grid from "../../components/Grid";
import Hero from "../../components/Hero";
import SearchBox from "../../components/SearchBox";
import { useNewReleases } from "../../queries/useNewReleases";
import { useTopRatedBooks } from "../../queries/useTopRatedBooks";
import "./HomePage.css";

const HomePage = () => {
  const { data: newReleases } = useNewReleases();
  const { data: topRatedBooks } = useTopRatedBooks();

  return (
    <body className="home-page">
      <div className="home-page__easy-search">
        <h2 className="home-page__easy-search__heading">Easy search...</h2>
        <SearchBox />
      </div>
      <Hero />
      <main className="home-page__main">
        <div className="home-page__new-releases">
          <h2 className="home-page__heading">New Releases</h2>
          <Grid>
            {newReleases?.map((book) => (
              <BookCard book={book} />
            ))}
          </Grid>
        </div>

        <div className="home-page__top-rated">
          <h2 className="home-page__heading">Top Rated</h2>
          <Grid>
            {topRatedBooks?.map((book) => (
              <BookCard book={book} />
            ))}
          </Grid>
        </div>
      </main>
      <Footer />
    </body>
  );
};

export default HomePage;
