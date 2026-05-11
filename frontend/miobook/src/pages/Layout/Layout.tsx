import { Outlet } from "react-router-dom";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import "./Layout.css";

const Layout = () => {
  return (
    <>
      <Header />
      <main>
        <Outlet />
      </main>
      <Footer />
    </>
  );
};

export default Layout;
