import { useEffect, useState } from "react";
import { Link } from "react-router";
import { useAuth } from "../context/auth-context";
import CardService from "../services/CardService";
import CardComponent from "../components/CardComponent";
import SearchBar from "../components/SearchBar";

export default function Home() {
  const { user, token } = useAuth();
  const [cards, setCards] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
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

  const filteredCards = cards.filter(card => card.name.toLowerCase().includes(searchTerm.toLowerCase()))

  return (
    <div className="flex flex-col items-center pb-5">

      <div id="top-bar" className="flex w-[80vw] justify-between pt-5 pb-5">
        {token ? ( <p>Welcome, {user.username}!</p> ) : ( <p>You are not logged in.</p> )}
        <p>This is the home page.</p>
        {user?.role==="ROLE_ADMIN" && <button>Create a card (unfinished)</button>}
        <SearchBar searchTerm={searchTerm} setSearchTerm={setSearchTerm} />
      </div>
      
      <div id="card-list" className="flex flex-wrap justify-center max-w-[80vw] gap-30 p-5 bg-slate-200">
        {loading ? <p>Loading cards...</p> : (
          cards.length === 0 ? ( <p>No cards found.</p> ) : (
              filteredCards.map((card) => (
                <Link to={`/cards/${card.id}`} key={card.id}>
                  <CardComponent card={card} isClickable={true}/>
                </Link>
                
              ))
            )
          )
        }
      </div>
      
    </div>
  );
}