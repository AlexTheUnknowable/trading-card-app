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

  return <div className="border-5 border-gray-400 flex flex-col items-center">
    <div id="top-bar" className="flex">
      {user ? <p>Welcome, {user.username}!</p> : <p>You are not logged in.</p>}
      <h2>This is the home page</h2>
      {user?.role==="ROLE_ADMIN" && <button>Create a card</button>}
      <input type="text" name="search" placeholder="Search yippeeeee" className="placeholder:text-gray-500 placeholder:italic"/>
    </div>
    <div id="card-list" className="flex flex-wrap justify-center max-w-[80vw] gap-30 bg-slate-100">
      {cards.map(card => (
        <CardComponent key={card.id} card={card}/>
      ))}
    </div>
  </div>;
}
