import { useCallback, useState } from "react";
import ItemService from "../services/ItemService";
import CardList from "../components/CardList";

export default function PullPage() {
  const [pulled, setPulled] = useState(false);
  const fetchData = useCallback(() => ItemService.pullPack(), []);
  
  return (<div className="flex flex-col items-center">
  {pulled ? 
    <CardList
      fetchData={fetchData}
      linkFunction={(item) => `/items/${item.itemId}`}
      titleBlurb={<>
        <p>These are the cards you just pulled.</p>
      </>}
    />
    :
    <button className="border-1 rounded-sm m-15" onClick={() => setPulled(true)}>
      Click to pull!
    </button>}
  </div>);
}