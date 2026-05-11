import { useState } from "react";
import AddAuthorModal from "../../components/AddAuthorModal";
import AddBookModal from "../../components/AddBookModal";
import AuthorsCard from "../../components/AuthorsCard";
import Backdrop from "../../components/Backdrop";
import BooksTableCard from "../../components/BooksTableCard";
import Button from "../../components/Button";
import UserAccount from "../../components/UserAccount";
import { useNoScroll } from "../../hooks/useNoScroll";
import "./AdminPage.css";

const AdminPage = () => {
  const [showAuthorModal, setShowAuthorModal] = useState(false);
  const [showBookModal, setShowBookModal] = useState(false);

  useNoScroll([showAuthorModal, showBookModal]);

  return (
    <body>
      <main>
        <Backdrop enabled={showAuthorModal || showBookModal} />
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
    </body>
  );
};

export default AdminPage;
