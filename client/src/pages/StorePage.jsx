import { useEffect, useState } from "react";
import { Link } from "react-router";
import ItemService from "../services/ItemService";
import CardComponent from "../components/CardComponent";

export default function StorePage() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
      ItemService.listStore()
        .then((data) => setItems(data))
        .catch((err) => console.error("Failed to fetch card:", err))
        .finally(() => setLoading(false));
    }, []);
  
  return (
    <div className="flex flex-col items-center pb-5">

      <div id="top-bar" className="flex w-[80vw] justify-between pt-5 pb-5">
        <p>This is the store.</p>
        <input type="text" name="search" placeholder="Search yippeeeee" className="border-1 border-gray-500 rounded-sm placeholder:text-gray-500 placeholder:italic placeholder:p-1"/>
      </div>
      
      <div id="card-list" className="flex flex-wrap justify-center max-w-[80vw] gap-30 p-5 bg-slate-200">
        {loading ? <p>Loading items...</p> : (
          items.length === 0 ? ( <p>No items found.</p> ) : (
            items.map((item) => (
              <Link to={`/items/${item.itemId}`} key={item.itemId}>
                <CardComponent card={item} isClickable={true}/>
              </Link>
            ))
          ))
        }
      </div>
      
    </div>
  );
}