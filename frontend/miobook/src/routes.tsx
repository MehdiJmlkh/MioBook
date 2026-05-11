import { createBrowserRouter } from "react-router-dom";
import Layout from "./pages/Layout";
import HomePage from "./pages/HomePage";
import SearchResultPage from "./pages/SearchResultPage";
import BookPage from "./pages/BookPage";
import BookContentPage from "./pages/BookContentPage";
import UserPage from "./pages/UserPage";
import CartPage from "./pages/CartPage";
import PurchaseHistoryPage from "./pages/PurchaseHistoryPage";
import AdminPage from "./pages/AdminPage";
import AuthorPage from "./pages/AuthorPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
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
]);

export default router;
