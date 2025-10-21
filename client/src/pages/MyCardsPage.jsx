import ItemService from "../services/ItemService";
import CardList from "../components/CardList";

export default function MyCardsPage() {
  return (
    <CardList
      fetchData={ItemService.getMyUniqueCards}
      linkFunction={(uniqueItem) => `/mycards/${uniqueItem.cardId}`}
      titleBlurb={<>
        <p>These are all cards you own.</p>
      </>}
    />
  );
}