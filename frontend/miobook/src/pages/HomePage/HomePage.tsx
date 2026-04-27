import BookCard from "../../components/BookCard";
import Footer from "../../components/Footer";
import Grid from "../../components/Grid";
import Header from "../../components/Header";
import Hero from "../../components/Hero";
import SearchBox from "../../components/SearchBox";
import "./HomePage.css";

const HomePage = () => {
  return (
    <body className="home-page">
      <Header />
      <div className="home-page__easy-search">
        <h2 className="home-page__easy-search__heading">Easy search...</h2>
        <SearchBox />
      </div>
      <Hero />
      <main className="home-page__main">
        <div className="home-page__new-releases">
          <h2 className="home-page__heading">New Releases</h2>
          <Grid>
            <BookCard />
            <BookCard />
            <BookCard />
            <BookCard />
            <BookCard />
          </Grid>
        </div>

        <div className="home-page__top-rated">
          <h2 className="home-page__heading">Top Rated</h2>
          <Grid>
            <BookCard />
            <BookCard />
            <BookCard />
            <BookCard />
            <BookCard />
          </Grid>
        </div>
      </main>
      <Footer />
    </body>
  );
};

export default HomePage;
