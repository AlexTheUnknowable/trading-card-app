import { Link } from "react-router";
import pokeballIcon from "../assets/pokeball-icon.png";

export default function ItemComponent({ item }) {
  return (
    <Link to={`/items/${item.id}`} className="bg-lime-500 hover:bg-lime-600">
      <img src={pokeballIcon} alt="image woop" className="max-w-[18em] h-auto mx-5 mt-5 bg-slate-500"/>
      <p className="text-center m-5">{item.name}</p>
    </Link>
  );
}