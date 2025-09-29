import { Navigate } from "react-router-dom";
import { useAuth } from "../context/auth-context";

export default function ProtectedRoute({ children }) {
  const { token } = useAuth();

  if (!token) {
    return <Navigate to="/login" replace />; // Not logged in → redirect to login
  }

  return children;
}