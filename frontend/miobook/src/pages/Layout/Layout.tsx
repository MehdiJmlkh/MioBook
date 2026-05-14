import { Outlet } from "react-router-dom";
import Footer from "../../components/Footer";
import Header from "../../components/Header";
import "./Layout.css";

const Layout = () => {
  return (
    <body className="page">
      <Header />
      <div>
        <Outlet />
      </div>
      <Footer />
    </body>
  );
};

export default Layout;
