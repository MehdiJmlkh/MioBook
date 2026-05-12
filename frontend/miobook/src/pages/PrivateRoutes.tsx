import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../queries/useAuth";
import Layout from "./Layout";

const PrivateRoutes = () => {
  const { data: user, isLoading, isError } = useAuth();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!user) {
    return <Navigate to="/sign-in" />;
  }

  return <Layout />;
};

export default PrivateRoutes;
