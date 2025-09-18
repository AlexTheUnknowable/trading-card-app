import React, { createContext, useContext, useEffect, useState } from "react";
import { authService, type User } from "../services/authService";
import api, { setAuthToken } from "../services/api";

interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (username: string, password: string) => Promise<void>;
  register: (data: Record<string, any>) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [initialised, setInitialised] = useState(false); // just for internal tracking

  // Client-only: read localStorage once on mount
  useEffect(() => {
    if (typeof window === "undefined") {
      setInitialised(true);
      return;
    }

    try {
      const storedUser = localStorage.getItem("user");
      const storedToken = localStorage.getItem("token");
      if (storedUser && storedToken) {
        setUser(JSON.parse(storedUser));
        setToken(storedToken);
        // sync axios header immediately on mount so future requests have auth
        setAuthToken(storedToken);
      }
    } catch (err) {
      console.error("Failed to load auth from localStorage", err);
    } finally {
      setInitialised(true);
    }
  }, []);

  // Keep axios header in sync whenever token changes (client-only)
  useEffect(() => {
    // setAuthToken is safe on server because it checks typeof window
    setAuthToken(token ?? null);
  }, [token]);

  async function login(username: string, password: string) {
    const res = await authService.login({ username, password });
    const { token: newToken, user: newUser } = res.data;
    setToken(newToken);
    setUser(newUser);
    if (typeof window !== "undefined") {
      localStorage.setItem("token", newToken);
      localStorage.setItem("user", JSON.stringify(newUser));
    }
    setAuthToken(newToken);
  }

  async function register(data: Record<string, any>) {
    await authService.register(data);
  }

  function logout() {
    setToken(null);
    setUser(null);
    if (typeof window !== "undefined") {
      localStorage.removeItem("token");
      localStorage.removeItem("user");
    }
    setAuthToken(null);
  }

  // IMPORTANT: do NOT conditionally call hooks above this line.
  // We render children always (initial user/token are null which matches SSR).
  return (
    <AuthContext.Provider value={{ user, token, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
}
