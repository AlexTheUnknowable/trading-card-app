import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/auth-context";

export default function Logout() {
  const { token, logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      logout(); // clears token + user
    }
    navigate("/"); // always go home
  }, [token, logout, navigate]);

  return <h1>{token ? "Logging out..." : "Redirecting..."}</h1>;
}