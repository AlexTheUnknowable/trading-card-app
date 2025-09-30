import pokeballIcon from "../assets/pokeball-icon.png";

export default function ItemComponent({ item }) {
  return (
    <div className="bg-lime-500 hover:bg-lime-600 p-5">
      <img src={pokeballIcon} alt="image woop" className="max-w-[18em] h-auto bg-slate-500"/>
      <p className="text-center mt-5">{item.name}</p>
    </div>
  );
}