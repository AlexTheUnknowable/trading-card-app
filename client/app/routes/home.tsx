import type { Route } from "./+types/home";
import { useAuth } from "../context/AuthContext";
import cardService from "../services/cardService";
import CardComponent from "../components/CardComponent";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Card Trader" },
    { name: "description", content: "yippeeee!!" },
  ];
}

export async function loader() {
  return cardService.list();
}

export async function clientLoader({
  serverLoader,
}: Route.ClientLoaderArgs) {
  return serverLoader();
}

export default function Home( { loaderData, actionData, params, matches }: Route.ComponentProps ) {
  const { user } = useAuth();
  const cards = loaderData;

  return <div>
    {user ? <p>Welcome, {user.username}!</p> : <p>You are not logged in.</p>}
    <div>
      <h2>This is the home page</h2>
      {user?.role==="ROLE_ADMIN" && <button>Create a card</button>}
      <p>Search bar</p>
    </div>
    <div>
      {cards.map(card => (
        <CardComponent key={card.id} card={card}/>
      ))}
    </div>
  </div>;
}
