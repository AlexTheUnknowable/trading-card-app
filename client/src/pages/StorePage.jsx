import { useEffect, useState } from "react";
import { Link } from "react-router";
import ItemService from "../services/ItemService";
import CardComponent from "../components/CardComponent";
import SearchBar from "../components/SearchBar";

export default function StorePage() {
  const [items, setItems] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
      ItemService.listStore()
        .then((data) => setItems(data))
        .catch((err) => console.error("Failed to fetch card:", err))
        .finally(() => setLoading(false));
  }, []);

  const filteredItems = items.filter(item => item.name.toLowerCase().includes(searchTerm.toLowerCase()))
  
  return (
    <div className="flex flex-col items-center pb-5">

      <div id="top-bar" className="flex w-[80vw] justify-between pt-5 pb-5">
        <p>This is the store.</p>
        <SearchBar searchTerm={searchTerm} setSearchTerm={setSearchTerm} />
      </div>
      
      <div id="card-list" className="flex flex-wrap justify-center max-w-[80vw] gap-30 p-5 bg-slate-200">
        {loading ? <p>Loading items...</p> : (
          items.length === 0 ? ( <p>No items found.</p> ) : (
            filteredItems.map((item) => (
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