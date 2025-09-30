import { useState } from "react";
import ItemService from "../services/ItemService";

export default function ItemInfoComponent({ item, userId }) {
    const [err, setErr] = useState('');
    const [newPrice, setNewPrice] = useState('');
    const [showSellForm, setShowSellForm] = useState(false);
    const [showConfirm, setShowConfirm] = useState(false);

    const isItemUpForSale = (item.price > 0);
    
    function buy() {
        console.log("buy") // implement later
    }

    function clickSell() {
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

    const takeDown = async (e) => {
        e.preventDefault();
        setErr("");
        try {
          const response = await ItemService.update(item.itemId, {
            itemId: item.itemId,
            userId: userId,
            cardId: item.cardId,
            price: null
          });
          console.log(response);
          window.location.reload();
        } catch (error) {
          console.error(error);
          setErr("An error occured.");
        }
        setShowConfirm(false);
    }

    // PUT requests shouldnt go thru if u arent the right user or u change more than the price. should work already on backend but test that lol
    return <div id="info" className="w-[20em] flex flex-col bg-gray-200 items-center">
        {(item.userId === userId) ? (
                // If the item is owned by the logged-in user
                (isItemUpForSale) ? (
                    // If the item is up for sale
                    <>
                    <p>You are selling this item.</p>
                    <button onClick={() => setShowConfirm(true)} className="navbtn">Take down</button>
                    {showConfirm && (
                        <div className="flex items-center justify-center">
                            <div className="text-center">
                                <p className="">Are you sure you want to take this item off the store?</p>
                                <div className="flex justify-center gap-4">
                                    <button onClick={takeDown} className="px-4 py-2 bg-purple-500 text-white rounded hover:bg-purple-600" >Yes, take down</button>
                                    <button onClick={() => setShowConfirm(false)} className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400" > Cancel </button>
                                </div>
                            </div>
                        </div>
                    )}
                    </>
                ) : (
                    // If the item isn't up for sale
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
                )
            ) : (
                // If the item is not owned by the logged-in user
                (isItemUpForSale) ? (
                    // If the item is up for sale
                    <>
                    <p>This item is up for sale.</p>
                    <button onClick={buy} className="w-[110px] h-[50px] flex items-center justify-center border-2 border-gray-700 rounded-sm bg-sky-200 text-xl hover:underline hover:bg-sky-300;">Buy</button>
                    </>
                ) : (
                    // If this item isn't up for sale
                    <>
                    <p>This item is not up for sale.</p>
                    </>
                )
            )
        }
      </div>
}