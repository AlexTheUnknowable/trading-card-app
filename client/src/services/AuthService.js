import api from "./api";

export default {
  login(user) {
    return api.post("/login", user);
  },

  register(user) {
    return api.post("/register", user);
  },
};