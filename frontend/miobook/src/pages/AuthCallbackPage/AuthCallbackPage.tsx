import Footer from "../../components/Footer";
import "./AuthCallbackPage.css"

const AuthCallbackPage = () => {
  return (
    <div className="callback-page">
    <div className="callback-page__content">
      <div className="spinner" />
      <p>Signing you in...</p>
    </div>
    <Footer />
    </div>
  );
};


export default AuthCallbackPage