import { Link } from "react-router";
import pokeballIcon from "../assets/pokeball-icon.png";

export default function CardComponent({ card }) {
  return (
    <Link to={`/cards/${card.id}`} className="bg-sky-500 hover:bg-sky-600">
      <img src={pokeballIcon} alt="image woop" className="max-w-[18em] h-auto mx-5 mt-5 bg-slate-500"/>
      <p className="text-center m-5">{card.name}</p>
    </Link>
  );
}