import ItemService from "../services/ItemService";
import CardList from "../components/CardList";

export default function StorePage() {
  return (
    <CardList
      fetchData={ItemService.listStore}
      linkFunction={(item) => `/items/${item.itemId}`}
      titleBlurb={<>
      <p>This is the store.</p>
      </>}
    />
  );
}