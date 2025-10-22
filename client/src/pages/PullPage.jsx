import { useCallback } from "react";
import ItemService from "../services/ItemService";
import CardList from "../components/CardList";

export default function PullPage() {
  const fetchData = useCallback(() => ItemService.pullPack(), []);
  
  return (
    <CardList
      fetchData={fetchData}
      linkFunction={(item) => `/items/${item.itemId}`}
      titleBlurb={<>
        <p>These are the cards you just pulled.</p>
      </>}
    />
  );
}