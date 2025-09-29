import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { useAuth } from "./context/auth-context";
import ProtectedRoute from "./routes/ProtectedRoute";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Logout from "./pages/Logout";
import Register from "./pages/Register";
import MyCardsPage from "./pages/MyCardsPage";
import CardPage from "./pages/CardPage";
import pokeballIcon from "./assets/pokeball-icon.png";

export default function App() {
  const { user, token } = useAuth();
  console.log(`User: ${user}`);

  return (
    <div className="flex flex-col">
      <header className="flex items-center justify-between border-5 border-red-400 bg-gray-200">
       <Link to="/" id="logo" className="flex items-center hover:underline">
          <img className="max-w-[5em] h-auto" src={pokeballIcon} alt="Pokeball icon" />
          <h1 id="title" className="text-[30px]">Card Trader</h1>
       </Link>
       <nav className="flex">
        {token ? (
          <>
          <Link to="/mycards" className="btn-header">My cards</Link>
          <Link to="/logout" className="btn-header">Log out</Link>
          </>
         ) : (
          <>
          <Link to="/login" className="btn-header">Log in</Link>
          <Link to="/register" className="btn-header">Register</Link>
          </>
        )}
       </nav>
      </header>
      <main>
       <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/logout" element={<Logout />} />
        <Route path="/cards/:cardId" element={<CardPage />} />

        <Route
          path="/mycards"
          element={
            <ProtectedRoute>
              <MyCardsPage />
            </ProtectedRoute>
          }
        />
      </Routes>
      </main>
      <footer className="bg-gray-200 border-5 border-red-400">
       This website is not affiliated with The Pokemon Company or Game Freak.
      </footer>
    </div>
  );
}
