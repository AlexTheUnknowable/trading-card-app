import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import CardService from "../services/CardService";
import pokeballIcon from "../assets/pokeball-icon.png";

export default function CardPage() {
  const { cardId } = useParams(); // gets cardId from the URL
  const [card, setCard] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    CardService.get(cardId)
      .then((data) => setCard(data))
      .catch((err) => console.error("Failed to fetch card:", err))
      .finally(() => setLoading(false));
  }, [cardId]);

  if (loading) return <p>Loading card...</p>;
  if (!card) return <p>Card not found.</p>;

  return (
    <div className="flex flex-col items-center border-2">
      <img src={card.imageUrl || pokeballIcon} alt={card.name} className="max-w-[20em] h-auto mb-4" />
      <h1 className="text-2xl font-bold mb-4">{card.name}</h1>
      <p>Rarity: {card.rarity}</p>
    </div>
  );
}
