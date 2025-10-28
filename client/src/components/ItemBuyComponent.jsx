import { useState } from "react";
import { useAuth } from "../context/auth-context";
import ItemService from "../services/ItemService";

export default function ItemBuyComponent({item, userId}) {
    const { updateUserBalance, user } = useAuth();
    const [err, setErr] = useState('');

    const buy = (e) => {
        if (confirm("Really buy this?")) {
            submitBuy(e);
        }
    }

    const submitBuy = async (e) => {
          e.preventDefault();
          setErr("");
          try {
            const response = await ItemService.purchase(item.itemId, {
              itemId: item.itemId,
              userId: userId,
              cardId: item.cardId,
              price: item.price
            });
            console.log(response);
            const newBalance = user.balance - item.price;
            updateUserBalance(newBalance);
            window.location.reload();
          } catch (error) {
            console.error(error);
            setErr("An error occured.");
          }
    };

    return (
        <>
        <p>This item is up for sale.</p>
        <button onClick={buy} className="w-[110px] h-[50px] flex items-center justify-center border-2 border-gray-700 rounded-sm bg-sky-200 text-xl hover:underline hover:bg-sky-300;">
            Buy
        </button>
        {err && <p style={{ color: "red" }}>{err}</p>}
        </>
    );
}