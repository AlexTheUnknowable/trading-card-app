import pokeballIcon from "../assets/pokeball-icon.png";
const typeColors = {
  normal: "bg-gray-400",
  fire: "bg-red-500",
  water: "bg-sky-500",
  grass: "bg-green-500",
  electric: "bg-yellow-400",
  ice: "bg-cyan-400",
  fighting: "bg-orange-700",
  poison: "bg-purple-500",
  ground: "bg-yellow-700",
  flying: "bg-indigo-400",
  psychic: "bg-pink-500",
  bug: "bg-lime-500",
  rock: "bg-stone-500",
  ghost: "bg-purple-700",
  dragon: "bg-violet-600",
  dark: "bg-neutral-600",
  steel: "bg-gray-500",
  fairy: "bg-rose-400",
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