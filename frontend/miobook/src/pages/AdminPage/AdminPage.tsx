import AuthorsCard from "../../components/AuthorsCard";
import BooksTableCard from "../../components/BooksTableCard";
import Button from "../../components/Button";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import UserAccount from "../../components/UserAccount";
import "./AdminPage.css";

const AdminPage = () => {
  return (
    <body>
      <Header />
      <main>
        <UserAccount className="user-account--large" />
        <span className="admin-page__btns">
          <Button className="btn-primary">Add Author</Button>
          <Button className="btn-primary">Add Book</Button>
        </span>
        <BooksTableCard className="author-page__books" />
        <AuthorsCard />
      </main>
      <Footer />
    </body>
  );
};

export default AdminPage;
