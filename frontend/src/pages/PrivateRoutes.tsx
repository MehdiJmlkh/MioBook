import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../queries/auth/useAuth";
import Layout from "./Layout";

const PrivateRoutes = () => {
  const { data: user, isLoading, error } = useAuth();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error || !user) {
    return <Navigate to="/sign-in" />;
  }

  return <Layout />;
};

export default PrivateRoutes;
