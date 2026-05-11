import { createBrowserRouter } from "react-router-dom";
import Layout from "./pages/Layout";
import HomePage from "./pages/HomePage";
import SearchResultPage from "./pages/SearchResultPage";
import BookPage from "./pages/BookPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        index: true,
        element: <HomePage />,
      },
      {
        path: "books",
        element: <SearchResultPage />,
      },
      {
        path: "books/:id",
        element: <BookPage />,
      },
    ],
  },
]);

export default router;
