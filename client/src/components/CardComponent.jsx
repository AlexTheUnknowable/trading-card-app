import pokeballIcon from "../assets/pokeball-icon.png";
const typeColors = {
  Normal: "bg-gray-400",
  Fire: "bg-red-500",
  Water: "bg-sky-500",
  Grass: "bg-green-500",
  Electric: "bg-yellow-400",
  Ice: "bg-cyan-400",
  Fighting: "bg-orange-700",
  Poison: "bg-purple-500",
  Ground: "bg-yellow-700",
  Flying: "bg-indigo-400",
  Psychic: "bg-pink-500",
  Bug: "bg-lime-500",
  Rock: "bg-stone-500",
  Ghost: "bg-purple-700",
  Dragon: "bg-violet-600",
  Dark: "bg-neutral-600",
  Steel: "bg-gray-500",
  Fairy: "bg-rose-400",
};

export default function CardComponent({ card, isClickable }) {
  const isItem = Object.prototype.hasOwnProperty.call(card, "price");
  const isUniqueItem = Object.prototype.hasOwnProperty.call(card, "countTotal");

  const bgStyle = typeColors[card.type] || "bg-slate-500";
  const clickableStyle = isClickable ? "hover:brightness-90" : "";

  return (
    <div className={`flex flex-col items-center p-5 w-min ${bgStyle} ${clickableStyle}`}>
      <img src={card.imgUrl || pokeballIcon} alt={card.name} className="max-w-[18em] h-auto bg-slate-700/25" />
      <p className="mt-5 font-bold">{card.name}</p>
      {isItem && (<>
        <p>Owner: {card.username}</p>
        <p>{card.price > 0 ? `Price: ${card.price}` : "Not selling"}</p>
      </>)}
      {isUniqueItem && (<>
        <p>Owned: {card.countTotal}</p>
        <p>Selling: {card.countWithPrice}</p>
      </>)}
    </div>
  );
}