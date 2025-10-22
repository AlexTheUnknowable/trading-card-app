import { useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Link, useLocation } from "react-router-dom";
import { useAuth } from "./context/auth-context";
import ProtectedRoute from "./routes/ProtectedRoute";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Logout from "./pages/Logout";
import Register from "./pages/Register";
import MyCardsPage from "./pages/MyCardsPage";
import MyCardsWithCardIdPage from "./pages/MyCardsWithCardIdPage";
import StorePage from "./pages/StorePage";
import CardPage from "./pages/CardPage";
import ItemPage from "./pages/ItemPage";
import PullPage from "./pages/PullPage";
import pokeballIcon from "./assets/pokeball-icon.png";

const routeMeta = {
    "/": { title: "Card Trader", favicon: "/favicon.ico" },
    "/login": { title: "Card Trader", favicon: "/favicon.ico" },
    "/register": { title: "Card Trader", favicon: "/favicon.ico" },
    "/mycards": { title: "Card Trader", favicon: "/hueh.png" },
  };

export default function App() {
  const { user, token } = useAuth();
  if (token) {console.log(`Username: ${user.username}`);}

  // favicon stuff
  const location = useLocation();
  useEffect(() => {
    const meta = routeMeta[location.pathname] || {};
    document.title = meta.title || "Card Trader";
    let link = document.querySelector("link[rel~='icon']");
    if (!link) {
      link = document.createElement("link");
      link.rel = "icon";
      document.getElementsByTagName("head")[0].appendChild(link);
    }
    link.href = meta.favicon || "/favicon.ico";
  }, [location.pathname]);

  return (
    <div className="flex flex-col min-h-screen">
      <header className="flex items-center justify-between bg-gray-200 p-2">
       <Link to="/" id="logo" className="flex items-center hover:underline">
          <img className="max-w-[5em] h-auto" src={pokeballIcon} alt="Pokeball icon" />
          <h1 id="title" className="text-[30px]">Card Trader</h1>
       </Link>
       <nav className="flex">
        {token ? (
          <>
          <Link to="/store" className="navbtn">Store</Link>
          <Link to="/mycards" className="navbtn">My cards</Link>
          <Link to="/logout" className="navbtn">Log out</Link>
          </>
         ) : (
          <>
          <Link to="/login" className="navbtn">Log in</Link>
          <Link to="/register" className="navbtn">Register</Link>
          </>
        )}
       </nav>
      </header>
      <main className="flex-grow">
       <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/logout" element={<Logout />} />

        <Route path="/cards/:cardId" element={<CardPage />} />
        <Route path="/items/:itemId" element={<ItemPage />} />

        <Route path="/store" element={<StorePage />} />
        <Route
          path="/mycards"
          element={
            <ProtectedRoute>
              <MyCardsPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="/mycards/:cardId"
          element={
            <ProtectedRoute>
              <MyCardsWithCardIdPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="/pull"
          element={
            <ProtectedRoute>
              <PullPage />
            </ProtectedRoute>
          }
        />
      </Routes>
      </main>
      <footer className="bg-gray-200 pl-1">
       This website is not affiliated with The Pokemon Company or Game Freak.
      </footer>
    </div>
  );
}
