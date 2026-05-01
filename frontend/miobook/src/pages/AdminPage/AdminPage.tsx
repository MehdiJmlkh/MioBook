import { useState } from "react";
import AddAuthorModal from "../../components/AddAuthorModal";
import AuthorsCard from "../../components/AuthorsCard";
import Backdrop from "../../components/Backdrop";
import BooksTableCard from "../../components/BooksTableCard";
import Button from "../../components/Button";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import UserAccount from "../../components/UserAccount";
import "./AdminPage.css";
import { useNoScroll } from "../../hooks/useNoScroll";
import AddBookModal from "../../components/AddBookModal/AddBookModal";

const AdminPage = () => {
  const [showAuthorModal, setShowAuthorModal] = useState(false);
  const [showBookModal, setShowBookModal] = useState(false);

  useNoScroll([showAuthorModal]);

  return (
    <body>
      <Header />
      <main>
        <Backdrop enabled={showAuthorModal} />
        <AddAuthorModal
          className={showAuthorModal ? "show" : ""}
          onClose={() => setShowAuthorModal(false)}
        />
        <AddBookModal
          className={showBookModal ? "show" : ""}
          onClose={() => setShowBookModal(false)}
        />
        <UserAccount className="user-account--large" />
        <span className="admin-page__btns">
          <Button
            className="btn-primary"
            onClick={() => setShowAuthorModal(true)}
          >
            Add Author
          </Button>
          <Button
            className="btn-primary"
            onClick={() => setShowBookModal(true)}
          >
            Add Book
          </Button>
        </span>
        <BooksTableCard className="author-page__books" />
        <AuthorsCard />
      </main>
      <Footer />
    </body>
  );
};

export default AdminPage;
