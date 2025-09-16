import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

// Don't touch localStorage here. Export a helper to set/remove the auth header.
export function setAuthToken(token?: string | null) {
  // only run in the browser
  if (typeof window === "undefined") return;

  if (token) {
    api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } else {
    delete api.defaults.headers.common["Authorization"];
  }
}

export default api;