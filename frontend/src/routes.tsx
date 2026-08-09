import { createBrowserRouter } from "react-router-dom";
import AdminPage from "./pages/AdminPage";
import AuthorPage from "./pages/AuthorPage";
import BookContentPage from "./pages/BookContentPage";
import BookPage from "./pages/BookPage";
import CartPage from "./pages/CartPage";
import HomePage from "./pages/HomePage";
import PrivateRoutes from "./pages/PrivateRoutes";
import PurchaseHistoryPage from "./pages/PurchaseHistoryPage";
import SearchResultPage from "./pages/SearchResultPage";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import UserPage from "./pages/UserPage";
import ErrorPage from "./pages/ErrorPage";
import AuthCallbackPage from "./pages/AuthCallbackPage";

const router = createBrowserRouter([
  {
    path: "/",
    errorElement: <ErrorPage />,
    children: [
      {
        element: <PrivateRoutes />,
        children: [
          { index: true, element: <HomePage /> },
          { path: "books", element: <SearchResultPage /> },
          { path: "books/:id", element: <BookPage /> },
          { path: "books/:id/content", element: <BookContentPage /> },
          { path: "user", element: <UserPage /> },
          { path: "user/cart", element: <CartPage /> },
          { path: "user/history", element: <PurchaseHistoryPage /> },
          { path: "admin", element: <AdminPage /> },
          { path: "authors/:id", element: <AuthorPage /> },
        ],
      },
      { path: "sign-in", element: <SignInPage /> },
      { path: "sign-up", element: <SignUpPage /> },
      { path: "auth/google/callback", element: <AuthCallbackPage /> },
    ],
  },
]);

export default router;
