import { isRouteErrorResponse, useRouteError } from "react-router-dom";
import Footer from "../../components/Footer";
import Link from "../../components/Link";
import "./ErrorPage.css";

const ErrorPage = () => {
  const error = useRouteError();

  let title;
  let message;
  let status;

  if (isRouteErrorResponse(error)) {
    status = 404;
    title = "Page Not Found";
    message = "Oops! The page you're looking for doesn't exist.";
  } else {
    status = 500;
    title = "Unexpected Error";
    message = "An error occurred while loading this page.";
  }

  if (error instanceof Error) {
    message = error.message;
  }

  return (
    <div className="error-page-container">
      <div className="error-page">
        <h1 className="error-page__heading">{status}</h1>
        <h2 className="error-page__sub-heading">{title}</h2>
        <p className="error-page__content">{message}</p>

        <Link to="/" className="link--primary error-page__home-btn">
          Go back home
        </Link>
      </div>

      <Footer />
    </div>
  );
};

export default ErrorPage;
