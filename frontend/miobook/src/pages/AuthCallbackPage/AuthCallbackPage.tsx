import { useSearchParams } from "react-router-dom";
import Footer from "../../components/Footer";
import "./AuthCallbackPage.css";
import { useGoogleAuth } from "../../queries/auth/useGoogleAuth";
import { useEffect } from "react";

const AuthCallbackPage = () => {
  const [searchParams] = useSearchParams();
  const returnedState = searchParams.get("state");

  const savedState = sessionStorage.getItem("oauth_state");

  if (returnedState !== savedState) {
    throw new Error("Possible CSRF attack");
  }

  const code = searchParams.get("code");

  const googleAuth = useGoogleAuth();

  useEffect(() => {
    if (code) {
      googleAuth.mutate(code);
    }
  }, [code]);

  console.log(code);

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

export default AuthCallbackPage;
