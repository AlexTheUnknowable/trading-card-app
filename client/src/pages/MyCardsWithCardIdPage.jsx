import { useParams } from "react-router-dom";
import ItemService from "../services/ItemService";
import CardList from "../components/CardList";

export default function MyCardsWithCardId() {
  const { cardId } = useParams();
  console.log(cardId);
  return (
    <CardList
      fetchData={() =>
        ItemService.getMyCards().then((items) =>
          items.filter((item) => item.cardId == cardId)
        )
      }
      linkFunction={(item) => `/items/${item.itemId}`}
      titleBlurb={<>
        <p>These are all the cards of this Pokemon that you own.</p>
      </>}
    />
  );
}