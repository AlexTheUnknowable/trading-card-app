import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import CardService from "../services/CardService";
import CardComponent from "../components/CardComponent";

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
    <div className="flex flex-col items-center">
      <CardComponent card={card} isClickable={false}/>
      <p>Rarity: {card.rarity}</p>
    </div>
  );
}
