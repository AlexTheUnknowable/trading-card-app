import React, { useState } from "react";
import { useNavigate } from "react-router";
import { useAuth } from "../context/AuthContext";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useAuth();
  const navigate = useNavigate();

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      await login(username, password);
      navigate("/");
    } catch (err: any) {
      alert(err.response?.data?.message || "Login failed");
    }
  }

  return (
    <form onSubmit={handleSubmit} className="max-w-[500px] m-8 mx-auto p-8 bg-[#f9f9f9] rounded-lg shadow-[0_4px_10px_rgba(0,0,0,0.1)]">
      <h1>Please Sign In</h1>
      <label className="text-base text-[#333] mb-2 block">
        Username
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          className="w-full p-3 text-base border border-[#ccc] rounded bg-white transition-colors duration-300 mb-4 focus:border-[lightsteelblue] focus:outline-none"
        />
      </label>
      <label className="text-base text-[#333] mb-2 block">
        Password
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="w-full p-3 text-base border border-[#ccc] rounded bg-white transition-colors duration-300 mb-4 focus:border-[lightsteelblue] focus:outline-none"
        />
      </label>
      <button type="submit" className="border-1 rounded-sm active:shadow-[inset_0_0_5px_#c1c1c1] focus:outline-none">Sign in</button>
    </form>
  );
}
