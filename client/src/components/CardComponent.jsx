import pokeballIcon from "../assets/pokeball-icon.png";

export default function CardComponent({ card, isClickable }) {
  const objectType = (Object.prototype.hasOwnProperty.call(card, "itemId") ? "item" : "card");

  const bgStyle = objectType === "item" ? "bg-lime-500" : "bg-sky-500";
  const clickableStyle = isClickable ? (objectType === "item" ? "hover:bg-lime-600" : "hover:bg-sky-600") : "";

  return (
    <div className={`flex flex-col items-center p-5 w-min ${bgStyle} ${clickableStyle}`}>
      <img src={card.imageUrl || pokeballIcon} alt={card.name} className="max-w-[18em] h-auto bg-slate-500" />
      <p className="mt-5 font-bold">{card.name}</p>
      {objectType === "item" && (
        <>
          <p>Owner: {card.username}</p>
          <p>Price: {card.price}</p>
        </>
      )}
    </div>
  );
  
}