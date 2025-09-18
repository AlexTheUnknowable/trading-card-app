import api from "./api";

export interface User {
  username: string;
  role: string;
  [key: string]: any;
}

export interface LoginResponse {
  token: string;
  user: User;
}

export const authService = {
  login: (credentials: { username: string; password: string }) =>
    api.post<LoginResponse>("/login", credentials),

  register: (user: Record<string, any>) =>
    api.post("/register", user),
};