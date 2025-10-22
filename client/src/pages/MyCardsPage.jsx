import { Link } from "react-router";
import ItemService from "../services/ItemService";
import CardList from "../components/CardList";

export default function MyCardsPage() {
  return (
    <CardList
      fetchData={ItemService.getMyUniqueCards}
      linkFunction={(uniqueItem) => `/mycards/${uniqueItem.cardId}`}
      titleBlurb={<>
        <p>These are all cards you own.</p>
        <Link to="/pull" className="">
          <button className="border-1 rounded-sm">
            Pull pack
          </button>
        </Link>
      </>}
    />
  );
}