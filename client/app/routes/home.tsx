import React from "react";
import { useAuth } from "../context/AuthContext";
import type { Route } from "./+types/home";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Card Trader" },
    { name: "description", content: "yippeeee!!" },
  ];
}

export default function Home( {loaderData, actionData, params, matches}: Route.ComponentProps ) {
  const { user } = useAuth();

  return <div>
    hey guys im the home page!
    {user ? <p>Welcome, {user.username}!</p> : <p>You are not logged in.</p>}
      <p>Loader Data (The data returned from the loader function in this route module): {JSON.stringify(loaderData)}</p>
      <p>Action Data (The data returned from the action function in this route module): {JSON.stringify(actionData)}</p>
      <p>Route Parameters (An object containing the route parameters (if any)): {JSON.stringify(params)}</p>
      <p>Matched Routes (An array of all the matches in the current route tree): {JSON.stringify(matches)}</p>
  </div>;
}
