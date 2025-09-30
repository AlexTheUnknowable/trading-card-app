import ItemTakeDownComponent from "./ItemTakeDownComponent";
import ItemSellComponent from "./ItemSellComponent";
import ItemBuyComponent from "./ItemBuyComponent";

export default function ItemInfoComponent({ item, userId }) {
  const isItemUpForSale = (item.price > 0);

  return <div id="info" className="w-[20em] flex flex-col bg-gray-200 items-center">
    {(item.userId === userId) ? (
        // If the item is owned by the logged-in user
        (isItemUpForSale) ? (
          // If the item is up for sale, let the user take it down
          <ItemTakeDownComponent item={item} userId={userId} />
        ) : (
          // If the item isn't up for sale, let the user sell it
          <ItemSellComponent item={item} userId={userId} />
        )
      ) : (
         // If the item is not owned by the logged-in user
        (isItemUpForSale) ? (
          // If the item is up for sale, let the user buy it
          <ItemBuyComponent />
        ) : (
          // If the item isn't up for sale
          <>
          <p>This item is not up for sale.</p>
          </>
        )
      )
    }
  </div>
}