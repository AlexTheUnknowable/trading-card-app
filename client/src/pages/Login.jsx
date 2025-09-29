import { useState } from "react";
import { useAuth } from "../context/auth-context";
import AuthService from "../services/AuthService";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const res = await AuthService.login({ username, password });

      const { token, user } = res.data; // backend should respond with token + user object
      login(token, user); // Save token + user into context (and localStorage via AuthProvider)

      navigate("/"); // redirect after login
    } catch (err) {
      console.error(err);
      setError("Invalid username or password. Please try again.");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-[500px] m-8 mx-auto p-8 bg-[#f9f9f9] rounded-lg shadow-[0_4px_10px_rgba(0,0,0,0.1)]">
        <h1>Please Sign In</h1>
        <label className="text-base text-[#333] mb-2 block">
          Username
          <input className="w-full p-3 text-base border border-[#ccc] rounded bg-white transition-colors duration-300 mb-4 focus:border-[lightsteelblue] focus:outline-none"
            type="text"
            placeholder=""
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </label>
        <label className="text-base text-[#333] mb-2 block">
          Password
          <input className="w-full p-3 text-base border border-[#ccc] rounded bg-white transition-colors duration-300 mb-4 focus:border-[lightsteelblue] focus:outline-none"
            type="password"
            placeholder=""
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </label>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <button type="submit" className="border-1 rounded-sm active:shadow-[inset_0_0_5px_#c1c1c1] focus:outline-none">Log in</button>
      </form>
  );
}
