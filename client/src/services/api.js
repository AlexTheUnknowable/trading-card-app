import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
});

// Optional: automatically attach token from localStorage if it exists
const token = localStorage.getItem("token");
if (token) {
  api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
}

export default api;