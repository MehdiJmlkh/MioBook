import Footer from "../../components/Footer";
import Header from "../../components/Header";
import PurchasedBooks from "../../components/PurchasedBooks";
import UserAccount from "../../components/UserAccount";
import Wallet from "../../components/Wallet";
import "./UserPage.css";

const UserPage = () => {
  return (
    <div className="page-container user-page">
      <UserAccount className="user-page__aacount" />
      <Wallet className="user-page__wallet" />
      <PurchasedBooks className="user-page__purchased-books" />
    </div>
  );
};

export default UserPage;
