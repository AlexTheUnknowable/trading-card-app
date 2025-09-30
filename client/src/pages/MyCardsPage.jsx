import ItemService from "../services/ItemService";
import CardList from "../components/CardList";

export default function MyCardsPage() {
  return (
    <CardList
      fetchData={ItemService.getMyCards}
      linkFunction={(item) => `/items/${item.itemId}`}
      titleBlurb={<>
      <p>These are your cards.</p>
      </>}
    />
  );
}