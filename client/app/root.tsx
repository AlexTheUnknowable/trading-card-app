import {
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
  isRouteErrorResponse
} from "react-router";
import type { Route } from "./+types/root";
import { AuthProvider, useAuth } from "./context/AuthContext";
import { ProtectedRoute } from "./components/ProtectedRoute";
import './app.css';
import pokeballIcon from "./assets/pokeball-icon.png";

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <head>
        <Meta />
        <Links />
      </head>
      <body>
        {children}
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <Main/>
    </AuthProvider>
  );
}

export function Main() {
  const { user, logout } = useAuth();

  return (
    <div className="flex flex-col h-[100vh] w-[100vw]">
      <header className="flex items-center justify-between">
       <div id="logo" className="flex">
          <img className="max-w-[5em] h-auto" src={pokeballIcon} alt="Pokeball icon" />
          <h1 id="title" className="">Card Trader</h1>
       </div>
       <nav className="flex">
        {user ? (
          <>
          <button onClick={logout} className="w-[110px] h-[50px] text-center bg-sky-200">Log out</button>
          </>
         ) : (
          <>
          <a href="/login" className="w-[110px] h-[50px] text-center bg-sky-200">Log in</a>
          <a href="/register" className="w-[110px] h-[50px] text-center bg-sky-200">Register</a>
          </>
        )}
       </nav>
      </header>
      <main>
        <Outlet />
      </main>
      <footer>
       This website is not affiliated with The Pokemon Company or Game Freak.
      </footer>
    </div>
  );
}

export function ErrorBoundary({ error }: Route.ErrorBoundaryProps) {
  let message = "Oops!";
  let details = "An unexpected error occurred.";
  let stack: string | undefined;

  if (isRouteErrorResponse(error)) {
    message = error.status === 404 ? "404" : "Error";
    details =
      error.status === 404
        ? "The requested page could not be found."
        : error.statusText || details;
  } else if (import.meta.env.DEV && error && error instanceof Error) {
    details = error.message;
    stack = error.stack;
  }

  return (
    <main className="pt-16 p-4 container mx-auto">
      <h1>{message}</h1>
      <p>{details}</p>
      {stack && (
        <pre className="w-full p-4 overflow-x-auto">
          <code>{stack}</code>
        </pre>
      )}
    </main>
  );
}
