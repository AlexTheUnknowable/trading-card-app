import pokeballIcon from "../assets/pokeball-icon.png";

export default function CardComponent({ card, isClickable }) {
  const objectType = (Object.prototype.hasOwnProperty.call(card, "itemId") ? "item" : "card");

  if (objectType === "card") return (
    <div className={isClickable ? "flex flex-col items-center bg-sky-500 hover:bg-sky-600 p-5 w-min" : "flex flex-col items-center bg-sky-500 p-5 w-min"}>
      <img src={card.imageUrl || pokeballIcon} alt="image woop" className="max-w-[18em] h-auto bg-slate-500"/>
      <p className="mt-5">{card.name}</p>
    </div>
  );

  if (objectType === "item") return (
    <div className={isClickable ? "flex flex-col items-center bg-lime-500 hover:bg-lime-600 p-5 w-min" : "flex flex-col items-center bg-lime-500 p-5 w-min"}>
      <img src={card.imageUrl || pokeballIcon} alt={card.name} className="max-w-[18em] h-auto bg-slate-500"/>
      <p className="font-bold">{card.name}</p>
      <p>Owner: {card.username}</p>
      <p>Price: {card.price}</p>
    </div>
  );
  
}