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
  const { user } = useAuth();

  return (
    <div className="flex flex-col h-[100vh] w-[100vw]">
      <header className="flex items-center justify-between border-5 border-red-400 bg-gray-200">
       <a href="/" id="logo" className="flex items-center hover:underline">
          <img className="max-w-[5em] h-auto" src={pokeballIcon} alt="Pokeball icon" />
          <h1 id="title" className="text-[30px]">Card Trader</h1>
       </a>
       <nav className="flex">
        {user ? (
          <>
          <a href="/logout" className="w-[110px] h-[50px] flex items-center justify-center border-2 border-gray-700 rounded-sm bg-sky-200 text-xl hover:underline hover:bg-sky-300">Log out</a>
          </>
         ) : (
          <>
          <a href="/login" className="w-[110px] h-[50px] flex items-center justify-center border-2 border-gray-700 rounded-sm bg-sky-200 text-xl hover:underline hover:bg-sky-300">Log in</a>
          <a href="/register" className="w-[110px] h-[50px] flex items-center justify-center border-2 border-gray-700 rounded-sm bg-sky-200 text-xl hover:underline hover:bg-sky-300">Register</a>
          </>
        )}
       </nav>
      </header>
      <main>
        <Outlet />
      </main>
      <footer className="bg-gray-200 border-5 border-red-400">
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
