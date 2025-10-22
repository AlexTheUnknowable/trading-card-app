import { useEffect, useState } from "react";
import { Link } from "react-router";
import CardComponent from "../components/CardComponent";
import SearchBar from "../components/SearchBar";

export default function CardList({ fetchData, linkFunction, titleBlurb }) {
  const [data, setData] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    console.log("Fetching pack...");
    fetchData()
      .then((data) => setData(data))
      .catch((err) => console.error("Failed to fetch data:", err))
      .finally(() => setLoading(false));
  }, [fetchData]);

  const filteredData = data.filter(card => card.name.toLowerCase().includes(searchTerm.toLowerCase()))
  
  return (
    <div className="flex flex-col items-center pb-5">

      <div id="top-bar" className="flex w-[80vw] justify-between pt-5 pb-5">
        {titleBlurb}
        <SearchBar searchTerm={searchTerm} setSearchTerm={setSearchTerm} />
      </div>
      
      <div id="card-list" className="flex flex-wrap justify-center max-w-[80vw] gap-30 p-5 bg-slate-200">
        {loading ? <p>Loading cards...</p> : (
          data.length === 0 ? ( <p>No cards found.</p> ) : (
              filteredData.map((card) => (
                <Link to={linkFunction(card)} key={card.id || card.itemId || card.cardId}>
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