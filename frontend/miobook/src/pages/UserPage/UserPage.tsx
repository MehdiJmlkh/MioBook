import Footer from "../../components/Footer";
import Header from "../../components/Header";
import PurchasedBooks from "../../components/PurchasedBooks";
import UserAccount from "../../components/UserAccount";
import Wallet from "../../components/Wallet";
import "./UserPage.css";

const UserPage = () => {
  return (
    <body>
      <main>
        <UserAccount className="user-page__aacount"/>
        <Wallet className="user-page__wallet" />
        <PurchasedBooks className="user-page__purchased-books"/>
      </main>
    </body>
  );
};

export default UserPage;
