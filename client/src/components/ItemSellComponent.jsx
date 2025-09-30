import { useState } from "react";
import ItemService from "../services/ItemService";

export default function ItemSellComponent({item, userId}) {
    const [err, setErr] = useState('');
    const [showSellForm, setShowSellForm] = useState(false);
    const [newPrice, setNewPrice] = useState('');

    const clickSell = () => {
        setShowSellForm(!showSellForm);
    }

    const submitSell = async (e) => {
            e.preventDefault();
            setErr("");
            try {
              const response = await ItemService.update(item.itemId, {
                itemId: item.itemId,
                userId: userId,
                cardId: item.cardId,
                price: newPrice
              });
              console.log(response);
              window.location.reload();
            } catch (error) {
              console.error(error);
              setErr("An error occured.");
            }
          };

    return (
        <>
        <p>You own this item.</p>
        <button onClick={clickSell} className="navbtn">Sell</button>
        {showSellForm && (
            <form onSubmit={submitSell}>
            <label className="mb-2 block">
                Sell price:
                <input className="w-full p-3 text-base border border-[#ccc] rounded bg-white transition-colors duration-300 focus:border-[lightsteelblue] focus:outline-none"
                    type="text"
                    value={newPrice}
                    onChange={(e) => setNewPrice(e.target.value)}
                    required
                />
            </label>
            {err && <p style={{ color: "red" }}>{err}</p>}
            <button type="submit" className="border-1 rounded-sm active:shadow-[inset_0_0_5px_#c1c1c1] focus:outline-none">Sell</button>
            </form>
        )}
        </>
    );
}