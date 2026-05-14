import { createBrowserRouter } from "react-router-dom";
import HomePage from "./pages/HomePage";
import SearchResultPage from "./pages/SearchResultPage";
import BookPage from "./pages/BookPage";
import BookContentPage from "./pages/BookContentPage";
import UserPage from "./pages/UserPage";
import CartPage from "./pages/CartPage";
import PurchaseHistoryPage from "./pages/PurchaseHistoryPage";
import AdminPage from "./pages/AdminPage";
import AuthorPage from "./pages/AuthorPage";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import PrivateRoutes from "./pages/PrivateRoutes";

const router = createBrowserRouter([
  {
    element: <PrivateRoutes />,
    children: [
      {
        path: "/",
        children: [
          { index: true, element: <HomePage /> },
          { path: "books", element: <SearchResultPage /> },
          { path: "books/:id", element: <BookPage /> },
          { path: "books/:id", element: <BookPage /> },
          { path: "books/:id/content", element: <BookContentPage /> },
          { path: "user", element: <UserPage /> },
          { path: "user/cart", element: <CartPage /> },
          { path: "user/history", element: <PurchaseHistoryPage /> },
          { path: "admin", element: <AdminPage /> },
          { path: "authors/:id", element: <AuthorPage /> },
        ],
      },
    ],
  },
  { path: "/sign-in", element: <SignInPage /> },
  { path: "/sign-up", element: <SignUpPage /> },
]);

export default router;
