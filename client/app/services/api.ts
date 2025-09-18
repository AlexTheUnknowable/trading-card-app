import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

export function setAuthToken(token?: string | null) {
  if (typeof window === "undefined") return;

  if (token) {
    api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } else {
    delete api.defaults.headers.common["Authorization"];
  }
}

export default api;