import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "../services/AuthService";

export default function Register() {
  const [user, setUser] = useState({
    username: "",
    password: "",
    confirmPassword: ""
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (user.password !== user.confirmPassword) {
      alert("Password & Confirm Password do not match");
      return;
    }

    try {
      const response = await AuthService.register({
        username: user.username,
        password: user.password,
      });

      if (response.status === 201) {
        alert("Thank you for registering, please sign in.");
        navigate("/login");
      }
    } catch (error) {
      if (!error.response) {
        alert(error);
      } else if (error.response.status === 400) {
        if (error.response.data.errors) {
          let msg = "Validation error: ";
          for (let err of error.response.data.errors) {
            msg += `'${err.field}': ${err.defaultMessage}. `;
          }
          alert(msg);
        } else {
          alert(error.response.data.message);
        }
      } else {
        alert(error.response.data.message);
      }
    }
  };

  return (
    <div id="register">
      <h1>Create Account</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Username:
          <input
            type="text"
            name="username"
            value={user.username}
            onChange={handleChange}
            required
            autoFocus
          />
        </label>
        <label>
          Password:
          <input
            type="password"
            name="password"
            value={user.password}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Confirm Password:
          <input
            type="password"
            name="confirmPassword"
            value={user.confirmPassword}
            onChange={handleChange}
            required
          />
        </label>
        <button type="submit">Create Account</button>
      </form>
      <hr />
      Already have an account? <a href="/login">Sign in!</a>
    </div>
  );
}
