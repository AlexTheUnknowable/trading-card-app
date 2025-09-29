import { useEffect, useState } from "react";
import ItemService from "../services/itemService";
import ItemComponent from "../components/ItemComponent";

export default function MyCardsPage() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
      ItemService.getMyCards()
        .then((data) => setItems(data))
        .catch((err) => console.error("Failed to fetch card:", err))
        .finally(() => setLoading(false));
    }, []);

  return (
    <div className="border-5 border-gray-400 flex flex-col items-center">
      <div id="card-list" className="flex flex-wrap justify-center max-w-[80vw] gap-30 bg-slate-200">
        {loading ? <p>Loading cards...</p> : (
          items.length === 0 ? ( <p>No items found.</p> ) : (
            items.map((item) => (
              <ItemComponent key={item.id} item={item}/>
            ))
          ))
        }
      </div>
    </div>
  );
}