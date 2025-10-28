import { useState } from "react";
import ItemService from "../services/ItemService";

export default function ItemTakeDownComponent({item, userId}) {
    const [err, setErr] = useState('');
    const [showTakeDownConfirm, setShowTakeDownConfirm] = useState(false);

    const takeDownConfirmation = (takeDown, setShowConfirm) => {
      return (
        <div className="flex items-center justify-center">
          <div className="text-center">
            <p className="">Are you sure you want to take this item off the store?</p>
            <div className="flex justify-center gap-4">
              <button onClick={takeDown} className="px-4 py-2 bg-purple-500 text-white rounded hover:bg-purple-600" >Yes, take down</button>
              <button onClick={() => setShowConfirm(false)} className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400" > Cancel </button>
            </div>
          </div>
        </div>
      );
    }

    const takeDown = async (e) => {
        e.preventDefault();
        setErr("");
        try {
          const response = await ItemService.update(item.itemId, {
            itemId: item.itemId,
            userId: userId,
            cardId: item.cardId,
            price: 0.00
          });
          console.log(response);
          window.location.reload();
        } catch (error) {
          console.error(error);
          setErr("An error occured.");
        }
        setShowTakeDownConfirm(false);
    }

    return (
        <>
        <p>You are selling this item.</p>
        <button onClick={() => setShowTakeDownConfirm(true)} className="navbtn">Take down</button>
        {showTakeDownConfirm && takeDownConfirmation(takeDown, setShowTakeDownConfirm)}
        {err && <p style={{ color: "red" }}>{err}</p>}
        </>
    );
}