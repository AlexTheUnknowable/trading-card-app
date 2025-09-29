import { useEffect, useState } from "react";
import { useAuth } from "../context/auth-context";
import CardService from "../services/CardService";
import CardComponent from "../components/CardComponent";

export default function Home() {
  const { user, token } = useAuth();
  console.log(user);
  const [cards, setCards] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    CardService.list()
      .then((data) => {
        setCards(data);
      })
      .catch((err) => {
        console.error("Failed to fetch cards:", err);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading cards...</p>;

  return (
    <div className="border-5 border-gray-400 flex flex-col items-center">

      <div id="top-bar" className="flex">
        {token ? ( <p>Welcome, {user.username}!</p> ) : ( <p>You are not logged in.</p> )}
        <p>This is the home page.</p>
        {user?.role==="ROLE_ADMIN" && <button>Create a card</button>}
        <input type="text" name="search" placeholder="Search yippeeeee" className="placeholder:text-gray-500 placeholder:italic"/>
      </div>
      
      <div id="card-list" className="flex flex-wrap justify-center max-w-[80vw] gap-30 bg-slate-200">
        {loading ? <p>Loading cards...</p> : (
          cards.length === 0 ? ( <p>No cards found.</p> ) : (
              cards.map((card) => (
                <CardComponent key={card.id} card={card}/>
              ))
            )
          )
        }
      </div>
      
    </div>
  );
}