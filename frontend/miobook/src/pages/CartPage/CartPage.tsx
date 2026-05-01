import Cart from "../../components/Cart/Cart";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import "./CartPage.css";

const CartPage = () => {
  return (
    <body>
      <Header className="cart-page__header" />
      <main>
        <Cart />
      </main>
      <Footer />
    </body>
  );
};

export default CartPage;
