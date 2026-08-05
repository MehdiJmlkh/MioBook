import { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import Footer from "../../components/Footer";
import { useGoogleAuth } from "../../queries/auth/useGoogleAuth";
import "./AuthCallbackPage.css";

const AuthCallbackPage = () => {
  const [searchParams] = useSearchParams();

  const returnedState = searchParams.get("state");
  const savedState = sessionStorage.getItem("oauth_state");
  
  if (returnedState !== savedState) {
    throw new Error("Possible CSRF attack");
  }
  
  const code = searchParams.get("code");
  const nonce = sessionStorage.getItem("oauth_nonce");

  const googleAuth = useGoogleAuth();
  
  useEffect(() => {
    if (code && nonce) {
      googleAuth.mutate({code, nonce});
    }
  }, [code, nonce]);

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
